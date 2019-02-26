package com.example.android.recipemanagernative;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoManager {

    private Context context;
    private String currentPhotoPath;

    public PhotoManager(Context context){
        this.context = context;
    }

    public boolean hasCamera(){

        // Checks if the device has camera functionality.
        boolean hasCamera = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);

        // Returns false if the device does not have camera functionality.
        if(!hasCamera){
            return false;
        }
        else{
            return true;
        }
    }

    public File createImage() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(fileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
