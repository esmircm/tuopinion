package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.entities.Place;

public interface PlacesDataProvider {

    void savePlace(Place place, OnFinishedInterfaceListener onFinishedInterfaceListener);
}
