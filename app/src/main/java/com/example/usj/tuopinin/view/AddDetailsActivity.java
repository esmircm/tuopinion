package com.example.usj.tuopinin.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import com.example.usj.tuopinin.TuOpinionApplication;
import com.example.usj.tuopinin.model.UserData;
import com.example.usj.tuopinin.presenter.AddDetailsPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.usj.tuopinin.Constants.PICK_IMAGE;
import static com.example.usj.tuopinin.Constants.REQUEST_TAKE_PHOTO;
import static com.example.usj.tuopinin.Constants.USER_ID;

@SuppressLint("Registered")
@EActivity(R.layout.activity_add_details)
public class AddDetailsActivity extends AppCompatActivity implements AddDetailsView {

    AddDetailsPresenter addDetailsPresenter;
    Uri photoURI;

    @Extra(USER_ID)
    long id;

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
        addDetailsPresenter = new AddDetailsPresenter(this, new UserData(((TuOpinionApplication) getApplication()).getDaoSession()));
    }

    @Override
    @Click(R.id.saveButton)
    public void checkAndSaveValues() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = getSelectedGender();
        addDetailsPresenter.saveUser(name, surname, phoneNumber, age, gender, id, photoURI);
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
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    @Click(R.id.cameraImageButton)
    public void takePhoto() {
        dispatchTakePictureIntent();
    }

    @Override
    public void showErrorToastMessage() {
        Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMapsActivity() {
        navigationHelper.openActivity(this, OpinionsMapsActivity.class);
    }

    @OnActivityResult(Constants.REQUEST_TAKE_PHOTO)
    void getPhotoDataFromCamera(int resultCode) {
        if (resultCode == RESULT_OK) {
            photoImageView.setImageURI(photoURI);
        }
    }

    @OnActivityResult(Constants.PICK_IMAGE)
    void getPhotoDataFromGallery(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                photoURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                    photoImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        photoImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        addDetailsPresenter.onDestroy();
        super.onDestroy();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("DetailsActicity", "Unable to create file");
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.usj.tuopinin.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }
}
