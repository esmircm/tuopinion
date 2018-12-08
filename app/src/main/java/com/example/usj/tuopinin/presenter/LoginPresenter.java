package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.model.UserDataProvider;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;
import com.example.usj.tuopinin.view.LoginView;

import java.util.List;

public class LoginPresenter {

    private LoginView loginView;
    private UserDataProvider dataProvider;
    private List<User> users;

    public LoginPresenter(LoginView loginView, UserDataProvider userDataProviderInterface) {
        this.loginView = loginView;
        this.dataProvider = userDataProviderInterface;
    }

    public void loginUser(String username, String password, OnRegisterListener onRegisterListener) {
        users = dataProvider.getUserWithSpecificUsernameAndPassword(username, password);
        if (users != null && users.size() > 0) {
            onRegisterListener.onSuccess(users.get(0).getId());
        } else {
            onRegisterListener.onError();
        }
    }

    public void registerUser(String username, String password, OnRegisterListener onRegisterListener) {
        dataProvider.registerUserCredentials(username, password, onRegisterListener);
    }
}

