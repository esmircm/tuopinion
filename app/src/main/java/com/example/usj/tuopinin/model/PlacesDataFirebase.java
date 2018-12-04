package com.example.usj.tuopinin.model;

import android.support.annotation.NonNull;

import com.example.usj.tuopinin.model.entities.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PlacesDataFirebase implements PlacesDataProvider {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public PlacesDataFirebase() {
        getFirebaseInstance();

    }

    void getFirebaseInstance() {
        database = FirebaseDatabase.getInstance();
        savePlacesIntoSql();
    }


    @Override
    public void savePlace(Place place, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        databaseReference = database.getReference("places");
        databaseReference.push().setValue(place);
    }

    private void savePlacesIntoSql() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Place> places = (List<Place>) dataSnapshot;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
