package com.example.usj.tuopinin.model;

import android.support.annotation.NonNull;

import com.example.usj.tuopinin.model.entities.DaoSession;
import com.example.usj.tuopinin.model.entities.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PlacesData implements PlacesDataProvider {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private DaoSession daoSession;

    public PlacesData(DaoSession daoSession) {
        getFirebaseInstance();
        this.daoSession = daoSession;
    }

    void getFirebaseInstance() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("places");
        savePlacesIntoSql();
    }


    @Override
    public void savePlace(Place place, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        databaseReference.push().setValue(place);
        onFinishedInterfaceListener.onSuccess();
    }

    @Override
    public List<Place> getAllPlaces() {
        return daoSession.getPlaceDao().loadAll();
    }

    private void savePlacesIntoSql() {
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
    }
}
