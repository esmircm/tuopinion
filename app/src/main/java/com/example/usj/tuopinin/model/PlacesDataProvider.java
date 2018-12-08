package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.entities.Place;

import java.util.List;

public interface PlacesDataProvider {

    void savePlace(Place place, OnFinishedInterfaceListener onFinishedInterfaceListener);

    List<Place> getAllPlaces();
}
