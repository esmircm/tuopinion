package com.example.usj.tuopinin.model;

import android.util.Log;

import com.example.usj.tuopinin.StringHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataProvider implements DataProviderInterface {

    DatabaseReference databaseReference;
    User user;

    public DataProvider() {
        getFirebaseInstance();
    }

    void getFirebaseInstance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
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
        String userId = databaseReference.push().getKey();
        if (StringHelper.notNullAndNotEmpty(userId)) {
            databaseReference.child(userId).setValue(user);
            onFinishedInterfaceListener.onFinished();
        }
    }

    @Override
    public void getUser(String userId, OnFinishedGettingUserDataListener listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = (User) dataSnapshot.getValue();
                listener.setUserData(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Error", databaseError.getMessage());
            }
        });
    }
}
