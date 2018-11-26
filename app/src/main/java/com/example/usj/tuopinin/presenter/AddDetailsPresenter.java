package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.DataProvider;
import com.example.usj.tuopinin.view.AddDetailsView;

public class AddDetailsPresenter {

    private AddDetailsView addDetailsView;
    private DataProvider dataProvider;

    public AddDetailsPresenter(AddDetailsView addDetailsView, DataProvider dataProvider) {
        this.addDetailsView = addDetailsView;
        this.dataProvider = dataProvider;
    }

    public void saveUser(String name, String surname, String phoneNumber, String age, String gender) {
        if (checkIfValuesAreEmpty(name, surname, phoneNumber, age, gender)) {
            dataProvider.saveUser(name, surname, phoneNumber, age, gender, () -> {
                if (addDetailsView != null) {
                    addDetailsView.openMapsActivity();
                }
            });
        } else {
            if (addDetailsView != null) {
                addDetailsView.showErrorToastMessage();
            }
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
    }

    public void takePhoto() {
    }

    public void onDestroy() {
        addDetailsView = null;
    }
}
