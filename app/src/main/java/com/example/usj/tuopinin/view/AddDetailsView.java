package com.example.usj.tuopinin.view;

import android.graphics.Bitmap;

public interface AddDetailsView {

    void checkAndSaveValues();

    void showErrorToastMessage();

    void openMapsActivity();

    void choosePhotoFromGallery();

    void takePhoto();

    void setPhoto(Bitmap bitmap);
}
