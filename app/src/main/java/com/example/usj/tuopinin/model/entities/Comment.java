package com.example.usj.tuopinin.model.entities;

import io.realm.RealmObject;
import java.io.Serializable;

public class Comment extends RealmObject implements Serializable {
    String comment;
    float rating;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
