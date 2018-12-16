package com.example.usj.tuopinin.view.interfaces;

import com.example.usj.tuopinin.model.entities.Comment;

import java.util.List;

public interface BottomCommentView {

    void onAddCommentAndPhotoButtonClick();

    void displayImages(List<String> images);

    void displayComments(List<Comment> comments);

    void hideImages();

    void hideComments();

    void addComment();
}
