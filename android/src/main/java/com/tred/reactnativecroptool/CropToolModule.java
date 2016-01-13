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
    public boolean crop(String photoPath,
                     int heightFactor,
                     int widthFactor) {

        String filePath = Environment.getExternalStorageDirectory() + "/" + photoPath;
        Bitmap image = BitmapFactory.decodeFile(filePath);
        if (image == null) {
            return false;
        }
        // Crop image
        int xCoordinate = image.getWidth() * (1 - widthFactor) / 2;
        int yCoordinate = image.getHeight() * (1 - heightFactor) / 2;
        Bitmap croppedPhoto = Bitmap.createBitmap(image, xCoordinate, yCoordinate,
                image.getHeight() * heightFactor, image.getWidth() * widthFactor);

        image.recycle();
        image = null;

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            return false;
        }
        // Write image to file
        croppedPhoto.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

        HashMap<String, Integer> result = new HashMap<>();
        result.put("width", croppedPhoto.getWidth());
        result.put("height", croppedPhoto.getHeight());

        croppedPhoto.recycle();
        croppedPhoto = null;
        return true;
    }
}
