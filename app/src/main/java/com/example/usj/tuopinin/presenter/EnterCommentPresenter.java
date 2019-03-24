package com.example.usj.tuopinin.presenter;

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

    public void saveComment(long placeId, String text, float rating, String photoURI) {
        if (StringHelper.notNullAndNotEmpty(text)) {
            placesDataProvider.saveComment(placeId, text, rating, photoURI);
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
