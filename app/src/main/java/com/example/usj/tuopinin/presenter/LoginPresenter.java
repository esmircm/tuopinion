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
        users = dataProvider.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && (user.getPassword().equals(password))) {
                onRegisterListener.onSuccess(user.getId());
            } else {
                onRegisterListener.onError();
            }
        }
    }

    public void registerUser(String username, String password, OnRegisterListener onRegisterListener) {
        dataProvider.registerUserCredentials(username, password, onRegisterListener);
    }
}

