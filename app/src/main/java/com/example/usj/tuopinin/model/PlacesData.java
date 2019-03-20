package com.example.usj.tuopinin.model;

import android.net.Uri;

import com.example.usj.tuopinin.model.entities.Place;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlacesData implements PlacesDataProvider {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public PlacesData() {
        getFirebaseInstance();

    }

    void getFirebaseInstance() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("places");
        //savePlacesIntoSql();
    }

 /*   @Override
    public List<Place> getAllPlaces() {
        return daoSession.getPlaceDao().loadAll();
    }*/

    @Override
    public List<Place> getAllPlaces() {
        return null;
    }

    @Override
    public void savePlace(String locationName, String description, Uri photoURI, double latitude, double longitude, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        Place place = new Place();
        place.setName(locationName);
        place.setDescription(description);
        place.setImage(String.valueOf(photoURI));
        place.setLatitude(latitude);
        place.setLongitude(longitude);
        databaseReference.push().setValue(place);
        onFinishedInterfaceListener.onSuccess();
    }

    @Override
    public void saveComment(String text, float rating, Uri photoURI, double latitude, double longitude) {

    }

/*    private void savePlacesIntoSql() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot placeSnapshot: dataSnapshot.getChildren()) {
                    Place place = placeSnapshot.getValue(Place.class);
                    daoSession.getPlaceDao().insert(place);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}