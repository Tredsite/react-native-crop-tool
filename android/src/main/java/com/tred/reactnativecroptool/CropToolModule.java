package com.tred.reactnativecroptool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

public class CropToolModule extends ReactContextBaseJavaModule {
    public CropToolModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNCropTool";
    }

    @ReactMethod
    public void crop(String photoPath,
                     float heightFactor,
                     float widthFactor,
                     Callback callback) {

        String filePath = photoPath.replaceFirst("file://", "");
        Bitmap image = BitmapFactory.decodeFile(filePath);
        if (image == null) {
            callback.invoke("Image could not be decoded to Bitmap", false);
            return;
        }
        // Crop image
        int xCoordinate = (int)(image.getWidth() * (1 - widthFactor) / 2);
        int yCoordinate = (int)(image.getHeight() * (1 - heightFactor) / 2);
        Bitmap croppedPhoto = Bitmap.createBitmap(image, xCoordinate, yCoordinate,
                              (int)(image.getWidth() * widthFactor), (int)(image.getHeight() * heightFactor));

        image.recycle();
        image = null;

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            callback.invoke(e.getMessage(), false);
            return;
        }
        // Write image to file
        croppedPhoto.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

        HashMap<String, Integer> result = new HashMap<>();
        result.put("width", croppedPhoto.getWidth());
        result.put("height", croppedPhoto.getHeight());

        croppedPhoto.recycle();
        croppedPhoto = null;
        callback.invoke(null, true);
        return;
    }
}

