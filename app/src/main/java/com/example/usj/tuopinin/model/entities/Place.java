package com.example.usj.tuopinin.model.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "place")
public class Place {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "description")
    private String description;
    @Property(nameInDb = "latitude")
    private double latitude;
    @Property(nameInDb = "longitude")
    private double longitude;
    @Property(nameInDb = "image")
    private String image;
    @Property(nameInDb = "mark")
    private int mark;
    @Generated(hash = 672222783)
    public Place(Long id, String name, String description, double latitude,
            double longitude, String image, int mark) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.mark = mark;
    }
    @Generated(hash = 1170019414)
    public Place() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getMark() {
        return this.mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
}
