package com.example.usj.tuopinin.model.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmPlace extends RealmObject {

    @PrimaryKey
    private long id;
    private RealmList<RealmComment> comments;
    private String description;
    private String image;
    private RealmList<String> images;
    private double latitude;
    private double longitude;
    private String name;
    private Float rating;
    private String firebaseId;

    public RealmPlace() {
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<RealmComment> getComments() {
        return comments;
    }

    public void setComments(final RealmList<RealmComment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public RealmList<String> getImages() {
        return images;
    }

    public void setImages(final RealmList<String> images) {
        this.images = images;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(final Float rating) {
        this.rating = rating;
    }
}