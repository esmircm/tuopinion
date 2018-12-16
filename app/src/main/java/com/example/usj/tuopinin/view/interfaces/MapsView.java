package com.example.usj.tuopinin.view.interfaces;

import com.example.usj.tuopinin.model.entities.Place;

import java.util.List;

public interface MapsView {
    void displayPlaces(List<Place> places);

    void openDetailsFragment(double latitude, double longitude);

    void openRegisterFragment(double latitude, double longitude);
}
