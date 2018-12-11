package com.example.usj.tuopinin.model.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "user")
public class User {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "username")
    private String username;
    @Property(nameInDb = "password")
    private String password;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "surname")
    private String surname;
    @Property(nameInDb = "gender")
    private String gender;
    @Property(nameInDb = "age")
    private String age;
    @Property(nameInDb = "phoneNumber")
    private String phoneNumber;
    @Property(nameInDb = "imageUri")
    private String imageUri;



    @Generated(hash = 586692638)
    public User() {
    }


    @Generated(hash = 1583679463)
    public User(Long id, String username, String password, String name,
            String surname, String gender, String age, String phoneNumber,
            String imageUri) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.imageUri = imageUri;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
