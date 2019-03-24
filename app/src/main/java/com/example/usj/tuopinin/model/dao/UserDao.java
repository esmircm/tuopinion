package com.example.usj.tuopinin.model.dao;

import android.util.Log;

import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.model.data_model.User;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.RealmUser;
import com.example.usj.tuopinin.model.mappers.UserMapper;

import io.realm.Realm;

public class UserDao {

    private Realm realm;

    public UserDao() {
        realm = Realm.getDefaultInstance();
    }

    public void insertUserDetails(RealmUser userToInsert, OnFinishedInterfaceListener onFinishedInterfaceListener) {

        realm.executeTransactionAsync(realm -> {
                    RealmUser user = realm.where(RealmUser.class).equalTo("username", userToInsert.getUsername()).findFirst();
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
                    Log.d("Realm", "RealmUser details successfully added");
                    onFinishedInterfaceListener.onSuccess();
                },
                error -> {
                    Log.d("Realm", "Failed adding user details.");
                    onFinishedInterfaceListener.onError();
                });
    }

    public void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener) {

        RealmUser user = UserMapper.mapToRealmUser(getUserWithSpecificUsernameAndPassword(username, password));
        if (user.getId() == 0) {
            long id = System.currentTimeMillis();
            realm.executeTransactionAsync(realm -> {
                RealmUser newUser = new RealmUser();
                newUser.setId(id);
                newUser.setUsername(username);
                newUser.setPassword(password);
                realm.insert(newUser);
            }, () -> {
                Log.d("Realm", "RealmUser successfully registered");
                onRegisterListener.onSuccess(id);
            }, error -> {
                Log.d("Realm", "RealmUser not registered");
                onRegisterListener.onError();
            });
        } else {
            onRegisterListener.onError();
        }
    }

    public User getUserWithSpecificUsernameAndPassword(String username, String password) {
       return UserMapper.mapToUser(realm.where(RealmUser.class).equalTo("username", username).equalTo("password", password).findFirst());
    }
}
