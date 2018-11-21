package com.example.usj.tuopinin;

import android.app.Activity;
import android.content.Intent;

import org.androidannotations.annotations.EBean;

@EBean
public class NavigationHelper {

    public void openActivity(Activity activity, Class targetActivity) {
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivity(intent);
    }
}
