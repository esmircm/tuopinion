package com.example.usj.tuopinin.presenter;

import com.example.usj.tuopinin.StringHelper;
import com.example.usj.tuopinin.model.UserDataProvider;
import com.example.usj.tuopinin.model.data_model.User;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.view.interfaces.LoginView;

public class LoginPresenter {

    private LoginView loginView;
    private UserDataProvider dataProvider;
    private User user;

    public LoginPresenter(LoginView loginView, UserDataProvider userDataProviderInterface) {
        this.loginView = loginView;
        this.dataProvider = userDataProviderInterface;
    }

    public void loginUser(String username, String password) {
        user = dataProvider.getUserWithSpecificUsernameAndPassword(username, password);
        if (user.getId() != 0) {
            loginView.openMapsActivity(user.getId());
        } else {
            loginView.showErrorMessage();
        }
    }

    public void registerUser(String username, String password) {
        if (StringHelper.notNullAndNotEmpty(username) && StringHelper.notNullAndNotEmpty(password)) {
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
        } else {
            loginView.askForUserCredentials();
        }

    }

    public void onDestroy() {
        loginView = null;
    }
}

