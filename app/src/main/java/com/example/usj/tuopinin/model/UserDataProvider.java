package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;

import java.util.List;

public interface UserDataProvider {
    void saveUserDetails(String name, String surname, String phoneNumber, String age, String gender, long id, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener);

    List<User> getAllUsers();

}
