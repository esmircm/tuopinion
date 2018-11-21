package com.example.usj.tuopinin;

import org.androidannotations.annotations.EBean;

@EBean
public class StringHelper {

    public boolean notNullAndNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
