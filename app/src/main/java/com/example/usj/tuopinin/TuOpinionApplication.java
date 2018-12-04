package com.example.usj.tuopinin;

import android.app.Application;

import com.example.usj.tuopinin.model.DbOpenHelper;
import com.example.usj.tuopinin.model.entities.DaoMaster;
import com.example.usj.tuopinin.model.entities.DaoSession;

public class TuOpinionApplication extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(
                new  DbOpenHelper(this, "tuopinion.db").getWritableDb()).newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
