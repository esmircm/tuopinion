package com.example.usj.tuopinin;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;

public class TuOpinionApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
