package com.example.usj.tuopinin.model.dao;

import com.example.usj.tuopinin.model.entities.Place;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;

public class PlacesDao {

    private Realm realm;

    public PlacesDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public List<Place> getAllPlaces() {
        return new ArrayList<>(realm.where(Place.class).findAll());
    }

    public void insertPlace(Place placeToInsert) {

        realm.executeTransactionAsync(realm -> {
            realm.createObject(Place.class, placeToInsert);
        });
    }
}


