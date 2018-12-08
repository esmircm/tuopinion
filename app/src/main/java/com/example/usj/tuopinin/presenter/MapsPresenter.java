package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.model.PlacesDataProvider;
import com.example.usj.tuopinin.view.MapsView;

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
}
