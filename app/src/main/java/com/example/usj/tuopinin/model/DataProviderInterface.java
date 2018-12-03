package com.example.usj.tuopinin.model;

public interface DataProviderInterface {
    void saveUserDetails(String name, String surname, String phoneNumber, String age, String gender, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void registerUserCredentials(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener);

    void loginUser(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener);

}
