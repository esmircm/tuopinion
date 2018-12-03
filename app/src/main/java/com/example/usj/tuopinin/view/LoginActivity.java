package com.example.usj.tuopinin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.model.DataProvider;
import com.example.usj.tuopinin.presenter.LoginPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@SuppressLint("Registered")
@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;

    @ViewById(R.id.username_edit_view)
    EditText usernameEditText;

    @ViewById(R.id.password_edit_view)
    EditText passwordEditText;

    @AfterViews
    void setupPresenter() {
        loginPresenter = new LoginPresenter(this, new DataProvider(getPreferences(Context.MODE_PRIVATE)));
    }

    @Override
    @Click(R.id.sign_in_button)
    public void onLoginButtonClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.loginUser(username, password, this::openAddDetailsActivity);
    }

    @Override
    @Click(R.id.register_button)
    public void onRegisterClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.registerUser(username, password, this::openAddDetailsActivity);
    }

    private void openAddDetailsActivity(){
        Intent intent = new Intent(this, AddDetailsActivity_.class);
        startActivity(intent);
    }
}
