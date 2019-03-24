package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.model.PlacesDataProvider;
import com.example.usj.tuopinin.model.data_model.Place;
import com.example.usj.tuopinin.view.interfaces.MapsView;

import java.util.List;

public class MapsPresenter {

    private MapsView mapsView;
    private PlacesDataProvider placesDataProvider;

    public MapsPresenter(MapsView mapsView, PlacesDataProvider placesDataProvider) {
        this.mapsView = mapsView;
        this.placesDataProvider = placesDataProvider;
    }

    public void displayPlaces() {
        mapsView.displayPlaces(placesDataProvider.getAllPlaces());
    }

    public void openDetailsFragment(double latitude, double longitude) {
        boolean openDetailsFragment = false;
        List<Place> places = placesDataProvider.getAllPlaces();

        if (places == null || places.size() == 0) {
            mapsView.openRegisterFragment(latitude, longitude);
        } else {

            for (Place place : places) {
                if (place.getLongitude() == longitude && place.getLatitude() == latitude) {
                    mapsView.openDetailsFragment(longitude, latitude, place.getId());
                    openDetailsFragment = true;
                }
            }
            if (!openDetailsFragment) {
                mapsView.openRegisterFragment(latitude, longitude);
            }
        }
    }

    public void onDestroy() {
        mapsView = null;
    }
}
