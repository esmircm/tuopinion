package com.example.usj.tuopinin.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.usj.tuopinin.model.dao.PlacesDao;
import com.example.usj.tuopinin.model.data_model.Comment;
import com.example.usj.tuopinin.model.data_model.Place;
import com.example.usj.tuopinin.model.entities.RealmComment;
import com.example.usj.tuopinin.model.entities.RealmPlace;
import com.example.usj.tuopinin.model.mappers.PlaceMapper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlacesData implements PlacesDataProvider {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    PlacesDao placesDao;
    private static PlacesData placesDataInstance = null;

    public static PlacesData getInstance() {
        if (placesDataInstance == null)
            placesDataInstance = new PlacesData();

        return placesDataInstance;
    }

    private PlacesData() {
        getFirebaseInstance();
        placesDao = new PlacesDao();
        registerPlaceFirebaseListener();
        // registerCommentFirebaseListener();
    }

    private void registerCommentFirebaseListener() {

    }

    private void registerPlaceFirebaseListener() {
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                List<Place> places = getAllPlaces();

                List<Comment> commentList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.child("comments").getChildren()) {
                    Comment comment = postSnapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                Place place = new Place();
                place.setName((String) dataSnapshot.child("name").getValue());
                place.setImage((String) dataSnapshot.child("image").getValue());
                place.setImages(Collections.singletonList((String) dataSnapshot.child("images").getValue()));
                place.setDescription((String) dataSnapshot.child("description").getValue());
                place.setId((long) dataSnapshot.child("id").getValue());
                place.setLatitude((double) dataSnapshot.child("latitude").getValue());
                place.setLongitude((double) dataSnapshot.child("longitude").getValue());
                //place.setRating((float) dataSnapshot.child("rating").getValue());

                place.setComments(commentList);

                RealmPlace realmPlace = PlaceMapper.mapToRealmPlace(place);
                realmPlace.setFirebaseId(dataSnapshot.getKey());

                if (placeIsNotAdded(realmPlace, places)) {
                    placesDao.insertPlace(realmPlace);
                    places.add(PlaceMapper.mapToPlace(realmPlace));
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                long placeId = (long) dataSnapshot.child("id").getValue();
                List<Comment> commentList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.child("comments").getChildren()) {
                    Comment comment = postSnapshot.getValue(Comment.class);
                    commentList.add(comment);
                }

                if (commentList.size() > 0) {
                    Comment comment = commentList.get(commentList.size() - 1);
                    RealmComment realmComment = new RealmComment();
                    realmComment.setId(System.currentTimeMillis());
                    realmComment.setComment(comment.getComment());
                    realmComment.setRating(comment.getRating());
                    realmComment.setPhotoUri(comment.getPhotoUri());

                    placesDao.saveComment(placeId, realmComment);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    void getFirebaseInstance() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("places");
    }

    @Override
    public List<Place> getAllPlaces() {
        return placesDao.getAllPlaces();
    }

    @Override
    public void savePlace(String locationName, String description, Uri photoURI, double latitude, double longitude, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        Place place = new Place();
        place.setId(System.currentTimeMillis());
        place.setName(locationName);
        place.setDescription(description);
        place.setImage(String.valueOf(photoURI));
        place.setImages(new ArrayList<>());
        place.setLatitude(latitude);
        place.setLongitude(longitude);
        place.setComments(new ArrayList<>());
        databaseReference.push().setValue(place);
        onFinishedInterfaceListener.onSuccess();
    }

    @Override
    public void saveComment(long placeId, String text, float rating, String photoData) {

        RealmPlace realmPlace = placesDao.getPlaceById(placeId);
        Comment comment = new Comment();
        comment.setId(System.currentTimeMillis());
        comment.setComment(text);
        comment.setRating(rating);
        comment.setPhotoUri(photoData);
        databaseReference.child(realmPlace.getFirebaseId()).child("comments").push().setValue(comment);
    }

    @Override
    public Place getPlaceById(long id) {
        return PlaceMapper.mapToPlace(placesDao.getPlaceById(id));
    }

    private Boolean placeIsNotAdded(RealmPlace realmPlace, List<Place> places) {
        boolean placeIsNotAdded = true;
        for (Place placeFromDb : places) {
            if (placeFromDb.getId() == realmPlace.getId()) {
                placeIsNotAdded = false;
            }
        }
        return placeIsNotAdded;
    }
}