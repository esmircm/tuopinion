package com.example.usj.tuopinin.model;

import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataProvider implements DataProviderInterface {

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private SharedPreferences sharedPreferences;
    private String uniqueKey;

    public DataProvider(SharedPreferences sharedPreference) {
        this.sharedPreferences = sharedPreference;
        getFirebaseInstance();
    }

    void getFirebaseInstance() {
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void saveUserDetails(String name, String surname, String phoneNumber, String age, String gender, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(uniqueKey + "name", name);
        editor.putString(uniqueKey + "surname", surname);
        editor.putString(uniqueKey + "phoneNumber", phoneNumber);
        editor.putString(uniqueKey + "age", age);
        editor.putString(uniqueKey + "gender", gender);
        editor.apply();
        onFinishedInterfaceListener.onSuccess();
    }

    @Override
    public void registerUserCredentials(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        uniqueKey = username;
        String savedUniqueKey = sharedPreferences.getString(uniqueKey, "");
        if (savedUniqueKey.equals("") || !savedUniqueKey.equals(uniqueKey)) {
            editor.putString(uniqueKey, username);
            editor.putString(uniqueKey + "password", password);
            editor.apply();
            onFinishedInterfaceListener.onSuccess();
        } else {
            onFinishedInterfaceListener.onError();
        }
    }

    @Override
    public void loginUser(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        uniqueKey = username;
        String savedUsername = sharedPreferences.getString(uniqueKey, "");
        String savedPassword = sharedPreferences.getString(uniqueKey + "password", "");
        if (savedUsername.equals(username) && savedPassword.equals(password)) {
            onFinishedInterfaceListener.onSuccess();
        } else {
            onFinishedInterfaceListener.onError();
        }
    }
}
