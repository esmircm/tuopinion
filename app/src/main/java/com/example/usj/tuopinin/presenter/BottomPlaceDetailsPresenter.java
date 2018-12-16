package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.Utils;
import com.example.usj.tuopinin.model.PlacesDataProvider;
import com.example.usj.tuopinin.model.entities.Comment;
import com.example.usj.tuopinin.model.entities.Place;
import com.example.usj.tuopinin.view.interfaces.BottomCommentView;

import java.util.ArrayList;
import java.util.List;

public class BottomPlaceDetailsPresenter {

    private BottomCommentView view;
    private PlacesDataProvider placesDataProvider;

    public BottomPlaceDetailsPresenter(BottomCommentView view, PlacesDataProvider placesDataProvider) {
        this.view = view;
        this.placesDataProvider = placesDataProvider;

    }

    public void displayImages(double latitude, double longitude) {
        List<Place> places = placesDataProvider.getAllPlaces();
        List<String> images = new ArrayList<>();
        for (Place place : places) {
            if (Utils.compareDouble(place.getLatitude(), latitude) && Utils.compareDouble(place.getLongitude(), longitude)) {
                images = place.getImages();
            }
        }

        if (images != null && images.size() > 0) {
            view.displayImages(images);
        } else {
            view.hideImages();
        }

    }

    public void displayComments(double latitude, double longitude) {
        List<Place> places = placesDataProvider.getAllPlaces();
        List<Comment> comments = new ArrayList<>();

        for (Place place : places) {
            if (Utils.compareDouble(place.getLatitude(), latitude) && Utils.compareDouble(place.getLongitude(), longitude)) {
                comments = place.getComments();
            }
        }

        if (comments != null && comments.size() > 0) {
            view.displayComments(comments);
        } else {
            view.hideComments();
        }
    }

    public void comment() {
        view.addComment();
    }

    public void onDestroy() {
        view = null;
    }
}
