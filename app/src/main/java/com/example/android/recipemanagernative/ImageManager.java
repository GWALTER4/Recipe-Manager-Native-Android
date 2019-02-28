package com.example.android.recipemanagernative;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageManager {

    private String imageFilePath; // Stores the file path of the image.
    private File imageFile; // Stores the temporary file.

    // Constructor for the ImageManager class.
    public ImageManager(){
        imageFilePath = null;
        imageFile = null;
    }

    public String getImageFilePath(){
        return imageFilePath;
    }

    public void deleteImageFile(){
        imageFile.delete();
    }

    // Creates a file for the photo to be stored in.
    public File prepareImageFile(Context context) throws IOException {

        // Gets a time stamp to append to the image file name.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Creates a file name for the image.
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Gets the application's directory.
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFile = File.createTempFile(imageFileName, ".jpg", directory);
        imageFilePath = imageFile.getPath();
        return imageFile;
    }

    // Creates a Uri for the image.
    public Uri createImageUri(Context context, File imageFile){
        return FileProvider.getUriForFile(context, "com.example.android.recipemanagernative", imageFile);
    }
}
