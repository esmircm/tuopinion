package com.example.usj.tuopinin.model;

import android.content.Context;
import android.util.Log;

import com.example.usj.tuopinin.model.entities.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
        }
    }
}