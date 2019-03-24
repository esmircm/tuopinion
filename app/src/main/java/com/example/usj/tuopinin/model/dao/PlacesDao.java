package com.example.usj.tuopinin.model.dao;

import com.example.usj.tuopinin.model.entities.RealmComment;
import com.example.usj.tuopinin.model.mappers.PlaceMapper;
import com.example.usj.tuopinin.model.data_model.Place;
import com.example.usj.tuopinin.model.entities.RealmPlace;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class PlacesDao {

    private Realm realm;

    public PlacesDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public List<Place> getAllPlaces() {

        List<RealmPlace> realmPlaces = realm.where(RealmPlace.class).findAll();

        List<Place> places = new ArrayList<>();
        for (RealmPlace realmPlace : realmPlaces) {
            places.add(PlaceMapper.mapToPlace(realmPlace));
        }

        return places;
    }

    public void insertPlace(RealmPlace placeToInsert) {
        realm.executeTransaction(bgRealm -> bgRealm.insert(placeToInsert));
    }

    public void saveComment(long placeId, RealmComment realmComment) {

        RealmPlace realmPlaceToUpdate = getPlaceById(placeId);

        realm.executeTransaction(bgRealm -> {

            if (realmPlaceToUpdate != null) {

                if (realmPlaceToUpdate.getComments() != null) {
                    realmPlaceToUpdate.getComments().add(realmComment);
                } else {
                    RealmList<RealmComment> comments = new RealmList<>();
                    comments.add(realmComment);
                    realmPlaceToUpdate.setComments(comments);
                }

                bgRealm.insertOrUpdate(realmPlaceToUpdate);
            }

        });
    }

    public RealmPlace getPlaceById(long id){
        return realm.where(RealmPlace.class).equalTo("id", id).findFirst();
    }
}

