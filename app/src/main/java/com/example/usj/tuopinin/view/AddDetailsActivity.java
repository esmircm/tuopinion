package com.example.usj.tuopinin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.usj.tuopinin.Constants;
import com.example.usj.tuopinin.NavigationHelper;
import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.model.DataProvider;
import com.example.usj.tuopinin.presenter.AddDetailsPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

@SuppressLint("Registered")
@EActivity(R.layout.activity_add_details)
public class AddDetailsActivity extends AppCompatActivity implements AddDetailsView {

    AddDetailsPresenter addDetailsPresenter;

    @Bean
    NavigationHelper navigationHelper;

    @ViewById
    EditText nameEditText;

    @ViewById
    EditText surnameEditText;

    @ViewById
    EditText ageEditText;

    @ViewById
    EditText phoneNumberEditText;

    @ViewById
    RadioGroup genderRadioGroup;

    @ViewById
    ImageView photoImageView;

    @ViewById
    Button saveButton;

    @AfterViews
    void setupPresenter() {
        addDetailsPresenter = new AddDetailsPresenter(this, new DataProvider(getPreferences(Context.MODE_PRIVATE)));
    }

    @Override
    @Click(R.id.saveButton)
    public void checkAndSaveValues() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = getSelectedGender();
        addDetailsPresenter.saveUser(name, surname, phoneNumber, age, gender);
    }

    public String getSelectedGender() {
        String gender = "";
        switch (genderRadioGroup.getCheckedRadioButtonId()) {
            case R.id.maleRadioButton:
                gender = "Male";
                break;
            case R.id.femaleRadioButton:
                gender = "Female";
                break;
            case R.id.otherRadioButton:
                gender = "Other";
                break;
            default:
                Log.e("AddDetailsActivity", "Unexpected value of radio button");
                break;
        }
        return gender;
    }


    @Override
    @Click(R.id.galleryImageButton)
    public void choosePhotoFromGallery() {
        addDetailsPresenter.choosePhotoFromGallery();
    }

    @Override
    @Click(R.id.cameraImageButton)
    public void takePhoto() {
        addDetailsPresenter.takePhoto();
    }

    @Override
    public void showErrorToastMessage() {
        Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMapsActivity() {
        navigationHelper.openActivity(this, OpinionsMapsActivity.class);
    }

    @OnActivityResult(Constants.REQUEST_IMAGE_CAMERA)
    void getPhotoDataFromCamera(int resultCode, Intent data) {
        addDetailsPresenter.setPhotoFromCamera(resultCode, data);
    }

    @OnActivityResult(Constants.PICK_IMAGE)
    void getPhotoDataFromGallery(int resultCode, Intent data) {
        addDetailsPresenter.setPhotoFromGallery(resultCode, data);
    }

    @Override
    public void setPhoto(Bitmap bitmap){
        photoImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        addDetailsPresenter.onDestroy();
        super.onDestroy();
    }
}
