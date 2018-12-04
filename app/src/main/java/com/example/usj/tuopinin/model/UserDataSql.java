package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.entities.DaoSession;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;

import java.util.List;

public class UserDataSql implements UserDataProvider {

    private DaoSession daoSession;

    public UserDataSql(DaoSession daoSession) {
        this.daoSession = daoSession;
    }


    @Override
    public void saveUserDetails(String name, String surname, String phoneNumber, String age, String gender, long id, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        List<User> users = daoSession.getUserDao().loadAll();
        User user = null;
        if (users != null && users.size() > 0 && users.get(0) != null) {
            for (User userFromDatabase : users) {
                if (userFromDatabase.getId() == id) {
                    user = userFromDatabase;
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPhoneNumber(phoneNumber);
                    user.setAge(age);
                    user.setGender(gender);
                    daoSession.getUserDao().update(user);
                    onFinishedInterfaceListener.onSuccess();
                }
            }
        }
        else {
            onFinishedInterfaceListener.onError();
        }

    }

    @Override
    public void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        daoSession.getUserDao().insert(user);
        List<User> users = daoSession.getUserDao().loadAll();
        if (users != null && users.size() > 0 && users.get(0) != null) {
            onRegisterListener.onSuccess(users.get(0).getId());
        } else {
            onRegisterListener.onError();
        }

    }

    @Override
    public List<User> getAllUsers() {
        return daoSession.getUserDao().loadAll();
    }
}
