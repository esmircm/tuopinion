package com.example.usj.tuopinin.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.TuOpinionApplication;
import com.example.usj.tuopinin.model.UserDataSql;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.presenter.LoginPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.example.usj.tuopinin.Constants.USER_ID;

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
        loginPresenter = new LoginPresenter(this, new UserDataSql(((TuOpinionApplication) getApplication()).getDaoSession()));
    }

    @Override
    @Click(R.id.sign_in_button)
    public void onLoginButtonClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.loginUser(username, password, new OnRegisterListener() {
            @Override
            public void onSuccess(long id) {
                openAddDetailsActivity(id);
            }

            @Override
            public void onError() {
                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    @Click(R.id.register_button)
    public void onRegisterClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.registerUser(username, password, new OnRegisterListener() {
            @Override
            public void onSuccess(long id) {
                openAddDetailsActivity(id);
            }

            @Override
            public void onError() {
                Toast.makeText(LoginActivity.this, "This username already exist.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAddDetailsActivity(long id) {
        Intent intent = new Intent(this, AddDetailsActivity_.class);
        intent.putExtra(USER_ID, id);
        startActivity(intent);
    }
}
