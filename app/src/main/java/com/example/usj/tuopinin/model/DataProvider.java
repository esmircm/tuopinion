package com.example.usj.tuopinin.model;

import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataProvider implements DataProviderInterface {

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private SharedPreferences sharedPreferences;

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
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("phoneNumber", phoneNumber);
        editor.putString("age", age);
        editor.putString("gender", gender);
        editor.apply();
        onFinishedInterfaceListener.onFinished();
    }

    @Override
    public void saveUserCredentials(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
        onFinishedInterfaceListener.onFinished();
    }

    @Override
    public void loginUser(String username, String password, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        String savedUsername = sharedPreferences.getString("username", "");
        String  savedPassword = sharedPreferences.getString("password", "");
        if (savedUsername.equals(username) && savedPassword.equals(password)){
            onFinishedInterfaceListener.onFinished();
        }
    }
}
