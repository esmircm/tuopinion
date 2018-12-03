package com.example.usj.tuopinin.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.usj.tuopinin.Constants;
import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.DataProviderInterface;
import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.view.AddDetailsView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.example.usj.tuopinin.Constants.PICK_IMAGE;

public class AddDetailsPresenter {

    private AddDetailsView addDetailsView;
    private DataProviderInterface dataProvider;

    public AddDetailsPresenter(AddDetailsView addDetailsView, DataProviderInterface dataProvider) {
        this.addDetailsView = addDetailsView;
        this.dataProvider = dataProvider;
    }

    public void saveUser(String name, String surname, String phoneNumber, String age, String gender) {
        if (checkIfValuesAreEmpty(name, surname, phoneNumber, age, gender)) {
            dataProvider.saveUserDetails(name, surname, phoneNumber, age, gender, new OnFinishedInterfaceListener() {
                @Override
                public void onSuccess() {
                    if (addDetailsView != null) {
                        addDetailsView.openMapsActivity();
                    }
                }

                @Override
                public void onError() {
                    if (addDetailsView != null) {
                        addDetailsView.showErrorToastMessage();
                    }
                }
            });
        }
    }

    private boolean checkIfValuesAreEmpty(String name, String surname, String phoneNumber, String age, String gender) {
        boolean enteredName = StringHelper.notNullAndNotEmpty(name);
        boolean enteredSurname = StringHelper.notNullAndNotEmpty(surname);
        boolean enteredPhoneNumber = StringHelper.notNullAndNotEmpty(phoneNumber);
        boolean enteredAge = StringHelper.notNullAndNotEmpty(age);
        boolean checkedGender = StringHelper.notNullAndNotEmpty(gender);
        return enteredName && enteredSurname && enteredPhoneNumber && enteredAge && checkedGender;
    }

    public void choosePhotoFromGallery() {
        if (addDetailsView != null) {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
            ((Activity) addDetailsView).startActivityForResult(chooserIntent, PICK_IMAGE);
        }
    }

    public void takePhoto() {
        if (addDetailsView != null) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(((Activity) addDetailsView).getPackageManager()) != null) {
                ((Activity) addDetailsView).startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAMERA);
            }
        }
    }

    public void setPhotoFromCamera(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && addDetailsView != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addDetailsView.setPhoto(imageBitmap);
        }
    }

    public void setPhotoFromGallery(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && addDetailsView != null) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(((Activity) addDetailsView).getContentResolver(), contentURI);
                    addDetailsView.setPhoto(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void onDestroy() {
        addDetailsView = null;
    }
}
