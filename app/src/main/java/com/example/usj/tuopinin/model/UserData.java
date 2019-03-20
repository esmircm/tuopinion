package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.dao.UserDao;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;

public class UserData implements UserDataProvider {

    private UserDao userDao;

    public UserData() {
        this.userDao = new UserDao();
    }

    @Override
    public void saveUserDetails(User user, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        userDao.insertUserDetails(user, onFinishedInterfaceListener);
    }

    @Override
    public void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener) {
        userDao.registerUserCredentials(username, password, onRegisterListener);
    }

    @Override
    public User getUserWithSpecificUsernameAndPassword(String username, String password) {
        return userDao.getUserWithSpecificUsernameAndPassword(username, password);
    }

}
