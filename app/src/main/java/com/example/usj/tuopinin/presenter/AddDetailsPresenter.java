package com.example.usj.tuopinin.presenter;

import android.net.Uri;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.model.UserDataProvider;
import com.example.usj.tuopinin.model.entities.User;
import com.example.usj.tuopinin.view.interfaces.AddDetailsView;

public class AddDetailsPresenter {

    private AddDetailsView addDetailsView;
    private UserDataProvider dataProvider;

    public AddDetailsPresenter(AddDetailsView addDetailsView, UserDataProvider dataProvider) {
        this.addDetailsView = addDetailsView;
        this.dataProvider = dataProvider;
    }

    public void saveUser(String name, String surname, String phoneNumber, String age, String gender, Uri photoURI) {
        if (checkIfValuesAreEmpty(name, surname, phoneNumber, age, gender)) {
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setPhoneNumber(phoneNumber);
            user.setAge(age);
            user.setGender(gender);
            user.setImageUri(photoURI.toString());
            dataProvider.saveUserDetails(user, new OnFinishedInterfaceListener() {
                @Override
                public void onSuccess() {
                    addDetailsView.openMapsActivity();
                }

                @Override
                public void onError() {
                    addDetailsView.showErrorToastMessage();
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

    public void onDestroy() {
        addDetailsView = null;
    }
}
