package com.example.usj.tuopinin.presenter;

import android.net.Uri;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.PlacesDataProvider;
import com.example.usj.tuopinin.view.interfaces.CommentView;

public class EnterCommentPresenter {

    private CommentView commentView;
    private PlacesDataProvider placesDataProvider;

    public EnterCommentPresenter(CommentView commentView, PlacesDataProvider placesDataProvider) {
        this.commentView = commentView;
        this.placesDataProvider = placesDataProvider;
    }

    public void saveComment(String text, float rating, Uri photoURI, double latitude, double longitude) {
        if (StringHelper.notNullAndNotEmpty(text)) {
            placesDataProvider.saveComment(text, rating, photoURI, latitude, longitude);
            commentView.closeFragment();
        }
        else {
            commentView.showErrorMessage();
        }
    }

    public void onDestroy() {
        commentView = null;
    }
}
