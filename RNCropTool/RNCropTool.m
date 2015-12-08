//
//  RNCropTool.m
//  RNCropTool
//
//  Created by Andrew Crowell on 12/7/15.
//  Copyright © 2015 TRED. All rights reserved.
//

#import "RNCropTool.h"
#import "RCTBridge.h"
@import CoreGraphics;

@implementation RNCropTool

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(crop:(NSString *)imagePath
                  heightFactor:(nonnull NSNumber *)hFactor
                  widthFactor:(nonnull NSNumber *)wFactor
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    @try {
        UIImage *image = [[UIImage alloc] initWithContentsOfFile:imagePath];
        float hFactorF = [hFactor floatValue];
        float wFactorF = [wFactor floatValue];

        // Create rectangle from middle of current image
        CGRect croprect = CGRectMake((image.size.width * (1-wFactorF) / 2), (image.size.height * (1-hFactorF) / 2),
                                     image.size.width * wFactorF, image.size.height * hFactorF);

        // Draw new image in current graphics context
        CGImageRef imageRef = CGImageCreateWithImageInRect([image CGImage], croprect);

        // Create new cropped UIImage
        UIImage *croppedImage = [UIImage imageWithCGImage:imageRef];
        [UIImageJPEGRepresentation(croppedImage, 1.0) writeToFile:imagePath atomically:YES];
        
        CGImageRelease(imageRef);
        NSDictionary *result = @{
                                 @"width": [NSNumber numberWithDouble:croppedImage.size.width],
                                 @"height": [NSNumber numberWithDouble:croppedImage.size.height]
                                 };

        resolve(result);
    }
    @catch (NSException *exception) {
        reject([NSError errorWithDomain:[exception reason] code:500 userInfo:NULL]);
    }
}

@end
