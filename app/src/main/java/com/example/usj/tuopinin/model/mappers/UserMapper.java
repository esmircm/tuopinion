package com.example.usj.tuopinin.model.mappers;

import com.example.usj.tuopinin.model.data_model.User;
import com.example.usj.tuopinin.model.entities.RealmUser;

public class UserMapper {

    public static User mapToUser(RealmUser realmUser) {
        User user = new User();
        if (realmUser != null) {
            user.setId(realmUser.getId());
            user.setAge(realmUser.getAge());
            user.setGender(realmUser.getGender());
            user.setImageUri(realmUser.getImageUri());
            user.setName(realmUser.getName());
            user.setPassword(realmUser.getPassword());
            user.setUsername(realmUser.getUsername());
            user.setPhoneNumber(realmUser.getPhoneNumber());
            user.setSurname(realmUser.getSurname());
        }
        return user;
    }

    public static RealmUser mapToRealmUser(User user) {
        RealmUser realmUser = new RealmUser();
        if (user != null) {
            realmUser.setId(user.getId());
            realmUser.setAge(user.getAge());
            realmUser.setGender(user.getGender());
            realmUser.setImageUri(user.getImageUri());
            realmUser.setName(user.getName());
            realmUser.setPassword(user.getPassword());
            realmUser.setUsername(user.getUsername());
            realmUser.setPhoneNumber(user.getPhoneNumber());
            realmUser.setSurname(user.getSurname());
        }
        return realmUser;
    }
}
