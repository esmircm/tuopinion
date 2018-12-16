package com.example.usj.tuopinin.view.interfaces;

public interface LoginView {

    void onLoginButtonClick();

    void onRegisterClick();

    void openMapsActivity(long id);

    void openAddDetailsActivity(long id);

    void showErrorMessage();

    void askForUserCredentials();
}
