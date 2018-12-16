package com.example.usj.tuopinin.model;

import android.net.Uri;

import com.example.usj.tuopinin.model.entities.Place;

import java.util.List;

public interface PlacesDataProvider {

    List<Place> getAllPlaces();

    void savePlace(String locationName, String description, Uri photoURI, double latitude,
                   double longitude, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void saveComment(String text, float rating, Uri photoURI, double latitude, double longitude);
}
