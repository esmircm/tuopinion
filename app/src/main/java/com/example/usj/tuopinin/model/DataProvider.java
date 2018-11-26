package com.example.usj.tuopinin.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataProvider implements DataProviderInterface {

    DatabaseReference databaseReference;
    User user;

    // Remove firebase dependency
    public DataProvider() {
        getFirebaseInstance();
    }

    void getFirebaseInstance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");
        user = new User();
    }

    @Override
    public void saveUser(String name, String surname, String phoneNumber, String age, String gender, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setPhoneNumber(phoneNumber);
        user.setAge(age);
        user.setGender(gender);
        databaseReference.setValue(user);
        onFinishedInterfaceListener.onFinished();
    }
}
