package com.example.usj.tuopinin.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.TuOpinionApplication;
import com.example.usj.tuopinin.model.UserData;
import com.example.usj.tuopinin.presenter.LoginPresenter;
import com.example.usj.tuopinin.view.interfaces.LoginView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.example.usj.tuopinin.Constants.USER_ID;

@SuppressLint("Registered")
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity implements LoginView {

    private LoginPresenter loginPresenter;

    @ViewById(R.id.username_edit_view)
    EditText usernameEditText;

    @ViewById(R.id.password_edit_view)
    EditText passwordEditText;

    @AfterViews
    void setupPresenter() {
        loginPresenter = new LoginPresenter(this, new UserData(((TuOpinionApplication) getApplication()).getDaoSession()));
    }

    @Override
    @Click(R.id.sign_in_button)
    public void onLoginButtonClick() {

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.loginUser(username, password);
    }

    @Override
    @Click(R.id.register_button)
    public void onRegisterClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.registerUser(username, password);
    }

    @Override
    public void openMapsActivity(long id) {
        Intent intent = new Intent(this, OpinionsMapsActivity.class);
        intent.putExtra(USER_ID, id);
        startActivity(intent);
    }

    @Override
    public void openAddDetailsActivity(long id) {
        Intent intent = new Intent(this, AddDetailsActivity_.class);
        intent.putExtra(USER_ID, id);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(LoginActivity.this, "User credentials are not valid", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void askForUserCredentials() {
        Toast.makeText(LoginActivity.this, "Choose your username and password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}
