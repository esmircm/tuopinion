package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.model.DataProviderInterface;
import com.example.usj.tuopinin.model.OnFinishedInterfaceListener;
import com.example.usj.tuopinin.view.LoginView;

public class LoginPresenter {

    private LoginView loginView;
    private DataProviderInterface dataProvider;

    public LoginPresenter(LoginView loginView, DataProviderInterface dataProviderInterface) {
        this.loginView = loginView;
        this.dataProvider = dataProviderInterface;
    }

    public void loginUser(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        dataProvider.loginUser(username, password, onFinishedInterfaceListener);
    }

    public void registerUser(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        dataProvider.saveUserCredentials(username, password, onFinishedInterfaceListener);
    }
}

