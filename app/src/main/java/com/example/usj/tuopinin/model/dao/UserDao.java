package com.example.usj.tuopinin.model.dao;

import android.util.Log;

import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;

import io.realm.Realm;

public class UserDao {

    private Realm realm;

    public UserDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public void insertUserDetails(User userToInsert, OnFinishedInterfaceListener onFinishedInterfaceListener) {

        realm.executeTransactionAsync(realm -> {
                    User user = realm.where(User.class).equalTo("username", userToInsert.getUsername()).findFirst();
                    if (user != null) {
                        user.setName(userToInsert.getName());
                        user.setSurname(userToInsert.getSurname());
                        user.setPassword(userToInsert.getPassword());
                        user.setPassword(userToInsert.getUsername());
                        user.setPhoneNumber(userToInsert.getPhoneNumber());
                        user.setSurname(userToInsert.getAge());
                        user.setSurname(userToInsert.getImageUri());
                        user.setSurname(userToInsert.getPhoneNumber());
                        user.setSurname(userToInsert.getGender());
                    }
                }, () -> {
                    Log.d("Realm", "User details successfully added");
                    onFinishedInterfaceListener.onSuccess();
                },
                error -> {
                    Log.d("Realm", "Failed adding user details.");
                    onFinishedInterfaceListener.onError();
                });
    }

    public void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener) {

        User user = getUserWithSpecificUsernameAndPassword(username, password);
        if (user == null) {
            long id = System.currentTimeMillis();
            realm.executeTransactionAsync(realm -> {
                User newUser = new User();
                newUser.setId(id);
                newUser.setUsername(username);
                newUser.setPassword(password);
                realm.insert(newUser);
            }, () -> {
                Log.d("Realm", "User successfully registered");
                onRegisterListener.onSuccess(id);
            }, error -> {
                Log.d("Realm", "User not registered");
                onRegisterListener.onError();
            });
        } else {
            onRegisterListener.onError();
        }
    }

    public User getUserWithSpecificUsernameAndPassword(String username, String password) {
        return realm.where(User.class).equalTo("username", username).equalTo("password", password).findFirst();
    }
}
