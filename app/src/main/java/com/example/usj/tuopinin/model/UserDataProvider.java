package com.example.usj.tuopinin.model;

import android.net.Uri;

import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;

import java.util.List;

public interface UserDataProvider {
    void saveUserDetails(String name, String surname, String phoneNumber, String age, String gender, long id, Uri photoUri, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener);

    List<User> getAllUsers();

    List<User> getUserWithSpecificUsernameAndPassword(String username, String password);

}
