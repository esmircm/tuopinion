package com.example.usj.tuopinin.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.usj.tuopinin.model.dao.PlacesDao;
import com.example.usj.tuopinin.model.entities.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.realm.RealmList;
import java.util.List;

public class PlacesData implements PlacesDataProvider {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    PlacesDao placesDao;

    public PlacesData() {
        getFirebaseInstance();
        placesDao = new PlacesDao();
    }

    void getFirebaseInstance() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("places");
    }

    @Override
    public List<Place> getAllPlaces() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Place place = postSnapshot.getValue(Place.class);
                    placesDao.insertPlace(mapPlace(place));
                }
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

return  placesDao.getAllPlaces();
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

    private Place mapPlace(Place fbPlace){
        Place rmPlace = new Place();

            rmPlace.setId(0);

        if (fbPlace.getDescription() == null) {
            rmPlace.setDescription("");
        }
        if (fbPlace.getImage() == null) {
            rmPlace.setImage("");
        }

        if (fbPlace.getName() == null) {
            rmPlace.setName("");
        }

        if (fbPlace.getComments() == null) {
            rmPlace.setComments(new RealmList<>());
        }

        if (fbPlace.getImages() == null) {
            rmPlace.setImages(new RealmList<>());
        }
        return rmPlace;
    }
}