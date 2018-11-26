package com.example.usj.tuopinin.model;

public interface DataProviderInterface {
    void saveUser(String name, String surname, String phoneNumber, String age, String gender, OnFinishedInterfaceListener onFinishedInterfaceListener);
}
