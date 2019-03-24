package com.example.usj.tuopinin.model.mappers;

import com.example.usj.tuopinin.model.data_model.Comment;
import com.example.usj.tuopinin.model.data_model.Place;
import com.example.usj.tuopinin.model.entities.RealmComment;
import com.example.usj.tuopinin.model.entities.RealmPlace;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class PlaceMapper {

    static public RealmPlace mapToRealmPlace(Place place) {
        RealmPlace realmPlace = new RealmPlace();
        realmPlace.setId(place.getId());
        realmPlace.setName(place.getName());

        if (place.getComments() != null) {
            RealmList<RealmComment> realmComments = new RealmList<>();
            List<Comment> comments = place.getComments();

            for (Comment comment : comments) {
                RealmComment realmComment = new RealmComment();
                realmComment.setId(comment.getId());
                realmComment.setComment(comment.getComment());
                realmComment.setRating(comment.getRating());
                realmComment.setPhotoUri(comment.getPhotoUri());
                realmComments.add(realmComment);
            }
            realmPlace.setComments(realmComments);
        }
        realmPlace.setDescription(place.getDescription());
        realmPlace.setImage(place.getImage());
        realmPlace.setLatitude(place.getLatitude());
        realmPlace.setLongitude(place.getLongitude());
        realmPlace.setRating(place.getRating());

        if (place.getImages() != null) {
            RealmList<String> realmImages = new RealmList<>();
            for (String image : place.getImages()) {
                realmImages.add(image);
            }
            realmPlace.setImages(realmImages);
        }

        return realmPlace;
    }

    static public Place mapToPlace(RealmPlace realmPlace) {
        Place place = new Place();
        place.setId(realmPlace.getId());
        place.setName(realmPlace.getName());

        if (realmPlace.getComments() != null) {
            List<Comment> comments = new ArrayList<>();
            for (RealmComment realmComment : realmPlace.getComments()) {
                Comment comment = new Comment();
                comment.setId(realmComment.getId());
                comment.setComment(realmComment.getComment());
                comment.setRating(realmComment.getRating());
                comment.setPhotoUri(realmComment.getPhotoUri());
                comments.add(comment);
            }
            place.setComments(comments);
        }

        place.setDescription(realmPlace.getDescription());
        place.setImage(realmPlace.getImage());
        place.setLatitude(realmPlace.getLatitude());
        place.setLongitude(realmPlace.getLongitude());
        place.setRating(realmPlace.getRating());

        if (realmPlace.getImages() != null) {
            List<String> images = new ArrayList<>();
            for (String image : realmPlace.getImages()) {
                images.add(image);
            }
            place.setImages(images);
        }
        return place;
    }

}
