package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;

public interface UserDataProvider {

    void saveUserDetails(User user, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener);

    User getUserWithSpecificUsernameAndPassword(String username, String password);

}
