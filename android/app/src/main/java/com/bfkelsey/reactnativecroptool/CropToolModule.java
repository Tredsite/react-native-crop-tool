package com.bfkelsey.reactnativecroptool;

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
                     int heightFactor,
                     int widthFactor,
                     Callback errorCallback,
                     Callback successCallback) {

        String filePath = Environment.getExternalStorageDirectory() + "/" + photoPath;
        Bitmap image = BitmapFactory.decodeFile(filePath);
        if (image == null) {
            errorCallback.invoke("Could not decode image.");
            return;
        }
        // Crop image
        int xCoordinate = image.getWidth() * (1 - widthFactor) / 2;
        int yCoordinate = image.getHeight() * (1 - heightFactor) / 2;
        Bitmap croppedPhoto = Bitmap.createBitmap(image, xCoordinate, yCoordinate,
                image.getHeight() * heightFactor, image.getWidth() * widthFactor);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            errorCallback.invoke(e.getMessage());
            return;
        }
        // Write image to file
        croppedPhoto.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

        HashMap<String, Integer> result = new HashMap<>();
        result.put("width", croppedPhoto.getWidth());
        result.put("height", croppedPhoto.getHeight());
        successCallback.invoke(result);
    }
}
