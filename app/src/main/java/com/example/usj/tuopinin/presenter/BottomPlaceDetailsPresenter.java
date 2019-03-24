package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.model.PlacesDataProvider;
import com.example.usj.tuopinin.model.data_model.Comment;
import com.example.usj.tuopinin.model.data_model.Place;
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

    public void displayImages(long placeId) {
        Place place = placesDataProvider.getPlaceById(placeId);
        List<Comment> comments = place.getComments();
        List<String> images = new ArrayList<>();

        for (Comment comment: comments) {
            images.add(comment.getPhotoUri());
        }

        if (images.size() > 0 && !images.contains(null)) {
            view.displayImages(images);
        } else {
            view.hideImages();
        }

    }

    public void displayComments(long placeId) {
        Place place = placesDataProvider.getPlaceById(placeId);
        List<Comment> comments = place.getComments();

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
