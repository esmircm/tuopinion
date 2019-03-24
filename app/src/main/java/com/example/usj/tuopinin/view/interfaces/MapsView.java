package com.example.usj.tuopinin.view.interfaces;

import com.example.usj.tuopinin.model.data_model.Place;

import java.util.List;

public interface MapsView {
    void displayPlaces(List<Place> places);

    void openDetailsFragment(double latitude, double longitude, long placeId);

    void openRegisterFragment(double latitude, double longitude);
}
