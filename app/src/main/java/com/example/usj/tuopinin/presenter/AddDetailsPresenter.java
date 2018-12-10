package com.example.usj.tuopinin.presenter;

import android.net.Uri;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.model.UserDataProvider;
import com.example.usj.tuopinin.view.AddDetailsView;

public class AddDetailsPresenter {

    private AddDetailsView addDetailsView;
    private UserDataProvider dataProvider;

    public AddDetailsPresenter(AddDetailsView addDetailsView, UserDataProvider dataProvider) {
        this.addDetailsView = addDetailsView;
        this.dataProvider = dataProvider;
    }

    public void saveUser(String name, String surname, String phoneNumber, String age, String gender, long id, Uri photoURI) {
        if (checkIfValuesAreEmpty(name, surname, phoneNumber, age, gender)) {
            dataProvider.saveUserDetails(name, surname, phoneNumber, age, gender, id, photoURI, new OnFinishedInterfaceListener() {
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


    public void onDestroy() {
        addDetailsView = null;
    }
}
