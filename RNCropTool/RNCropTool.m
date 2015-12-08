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

RCT_EXPORT_METHOD(crop:(NSString *)imagePath callback:(RCTResponseSenderBlock)callback)
{
    UIImage *image = [[UIImage alloc] initWithContentsOfFile:(NSString *)imagePath];
    NSLog([image description]);
    
//    // Create rectangle from middle of current image
//    CGRect croprect = CGRectMake(image.size.width / 4, image.size.height / 4 ,
//                                 (image.size.width / 2), (image.size.height / 2));
//    
//    // Draw new image in current graphics context
//    CGImageRef imageRef = CGImageCreateWithImageInRect([image CGImage], croprect);
//    
//    // Create new cropped UIImage
//    UIImage *croppedImage = [UIImage imageWithCGImage:imageRef];
//    
//    CGImageRelease(imageRef);
}

@end
