package com.example.usj.tuopinin.model;

import android.net.Uri;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.Utils;
import com.example.usj.tuopinin.model.entities.Comment;
import com.example.usj.tuopinin.model.entities.Place;

import java.util.ArrayList;
import java.util.List;

// This is just an example implementation of PlaceDataProvider. It should be changed with Firebase or SqlLite..

public class CachePlaces implements PlacesDataProvider {
    private ArrayList<Place> places;
    private ArrayList<String> images;
    private ArrayList<Comment> comments;
    private static CachePlaces sCachePlaces;

    public static CachePlaces getInstance() {
        if (sCachePlaces == null)
            sCachePlaces = new CachePlaces();

        return sCachePlaces;
    }

    private CachePlaces() {
        places = new ArrayList<>();
        images = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public List<Place> getAllPlaces() {
        return places;
    }

    @Override
    public void savePlace(String locationName, String description, Uri photoURI, double latitude, double longitude, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        Place place = new Place();
        place.setName(locationName);
        place.setDescription(description);
        place.setImage(String.valueOf(photoURI));
        place.setLongitude(longitude);
        place.setLatitude(latitude);
        places.add(place);
        onFinishedInterfaceListener.onSuccess();
    }

    @Override
    public void saveComment(String text, float rating, Uri photoURI, double latitude, double longitude) {
        for (Place place : places) {
            if (Utils.compareDouble(place.getLatitude(), latitude) && Utils.compareDouble(place.getLongitude(), longitude)) {
                Comment comment = new Comment();
                comment.setComment(text);
                comment.setRating(rating);

                if (place.getImages() == null) {
                    images = new ArrayList<>();
                } else {
                    images = (ArrayList<String>) place.getImages();
                }

                if (place.getComments() == null) {
                    comments = new ArrayList<>();
                } else {
                    comments = (ArrayList<Comment>) place.getComments();
                }

                if (StringHelper.notNullAndNotEmpty(comment.getComment()) && comment.getRating() != 0) {
                    comments.add(comment);
                }

                if (StringHelper.notNullAndNotEmpty(String.valueOf(photoURI))) {
                    images.add(String.valueOf(photoURI));
                }

                place.setImages(images);
                place.setComments(comments);
            }
        }
    }
}
