package com.example.usj.tuopinin.presenter;

import android.net.Uri;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.model.PlacesDataProvider;
import com.example.usj.tuopinin.view.interfaces.RegisterView;

public class RegisterLocationPresenter {
    private RegisterView registerView;
    private PlacesDataProvider placesDataProvider;

    public RegisterLocationPresenter(RegisterView registerView, PlacesDataProvider placesDataProvider) {
        this.registerView = registerView;
        this.placesDataProvider = placesDataProvider;
    }

    public void saveLocation(String locationName, String description,
                             Uri photoURI, double latitude, double longitude) {

        if (StringHelper.notNullAndNotEmpty(locationName) && StringHelper.notNullAndNotEmpty(description)) {

            placesDataProvider.savePlace(locationName, description, photoURI, latitude, longitude, new OnFinishedInterfaceListener() {
                @Override
                public void onSuccess() {
                    registerView.closeFragment();
                }

                @Override
                public void onError() {
                    registerView.showErrorMessage();
                }
            });
        } else {
            registerView.showErrorMessage();
        }
    }

    public void onDestroy() {
        registerView = null;
    }
}
