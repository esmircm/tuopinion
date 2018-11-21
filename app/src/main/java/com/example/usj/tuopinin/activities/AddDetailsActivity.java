package com.example.usj.tuopinin.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.usj.tuopinin.NavigationHelper;
import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@SuppressLint("Registered")
@EActivity(R.layout.activity_add_details)
public class AddDetailsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    User user;

    @Bean
    StringHelper stringHelper;

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

    @AfterInject
    void getFirebaseInstance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");
        user = new User();
    }

    @Click(R.id.saveButton)
    void save() {
        if (checkIfValuesAreEmpty()) {
            user.setName(nameEditText.getText().toString());
            user.setSurname(surnameEditText.getText().toString());
            user.setPhoneNumber(phoneNumberEditText.getText().toString());
            user.setAge(ageEditText.getText().toString());
            user.setGender(getSelectedGender());
            databaseReference.setValue(user);
            navigationHelper.openActivity(this, OpinionsMapsActivity.class);
        } else {
            Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkIfValuesAreEmpty() {
        boolean enteredName = stringHelper.notNullAndNotEmpty(nameEditText.getText().toString());
        boolean enteredSurname = stringHelper.notNullAndNotEmpty(surnameEditText.getText().toString());
        boolean enteredPhoneNumber = stringHelper.notNullAndNotEmpty(phoneNumberEditText.getText().toString());
        boolean enteredAge = stringHelper.notNullAndNotEmpty(ageEditText.getText().toString());
        boolean checkedGender = genderRadioGroup.getCheckedRadioButtonId() != -1;
        return enteredName && enteredSurname && enteredPhoneNumber && enteredAge && checkedGender;
    }

    private String getSelectedGender() {
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
                Log.e("AddDetailsActivity", "UNexpected value of radio button");
                break;
        }
        return gender;
    }

    @Click(R.id.cameraImageButton)
    void takePhoto(){}

    @Click(R.id.galleryImageButton)
    void chosePhotoFromGallery(){}
}
