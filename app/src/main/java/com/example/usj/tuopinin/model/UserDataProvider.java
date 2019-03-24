package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.data_model.User;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.RealmUser;

public interface UserDataProvider {

    void saveUserDetails(RealmUser user, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener);

    User getUserWithSpecificUsernameAndPassword(String username, String password);

}
