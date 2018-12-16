package com.example.usj.tuopinin.view;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.usj.tuopinin.R;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import static com.example.usj.tuopinin.Constants.ENTER_COMMENT;
import static com.example.usj.tuopinin.Constants.FRAGMENT_NAME;
import static com.example.usj.tuopinin.Constants.LATITUDE;
import static com.example.usj.tuopinin.Constants.LONGITUDE;
import static com.example.usj.tuopinin.Constants.REGISTER_LOCATION;

@SuppressLint("Registered")
@EActivity(R.layout.activity_add_location_details)
public class AddLocationDetailsActivity extends AppCompatActivity {

    @Extra(FRAGMENT_NAME)
    String fragmentName;

    @Extra(LONGITUDE)
    double longitude;

    @Extra(LATITUDE)
    double latitude;

    private FragmentManager fragmentManager;

    @AfterExtras
    void setFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }

    @AfterViews
    void openFragment() {
        if (fragmentName.equals(REGISTER_LOCATION)) {
            RegisterFragment registerFragment =
                    RegisterFragment.newInstance(latitude, longitude);
            fragmentManager.beginTransaction().add(R.id.content_layout, registerFragment).commit();
        } else if (fragmentName.equals(ENTER_COMMENT)) {
            CommentFragment commentFragment = CommentFragment.newInstance(latitude, longitude);
            fragmentManager.beginTransaction().add(R.id.content_layout, commentFragment).commit();
        }
    }
}
