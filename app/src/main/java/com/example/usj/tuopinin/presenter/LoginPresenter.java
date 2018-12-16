package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.model.UserDataProvider;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;
import com.example.usj.tuopinin.view.interfaces.LoginView;

import java.util.List;

public class LoginPresenter {

    private LoginView loginView;
    private UserDataProvider dataProvider;
    private List<User> users;

    public LoginPresenter(LoginView loginView, UserDataProvider userDataProviderInterface) {
        this.loginView = loginView;
        this.dataProvider = userDataProviderInterface;
    }

    public void loginUser(String username, String password) {
        users = dataProvider.getUserWithSpecificUsernameAndPassword(username, password);
        if (users != null && users.size() > 0) {
            loginView.openMapsActivity(users.get(0).getId());
        } else {
            loginView.showErrorMessage();
        }
    }

    public void registerUser(String username, String password) {
        dataProvider.registerUserCredentials(username, password, new OnRegisterListener() {
            @Override
            public void onSuccess(long id) {
                loginView.openAddDetailsActivity(id);
            }

            @Override
            public void onError() {
                loginView.showErrorMessage();
            }
        });
    }

    public void onDestroy() {
        loginView = null;
    }
}

