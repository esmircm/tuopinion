package com.example.usj.tuopinin.model;

import com.example.usj.tuopinin.model.entities.DaoSession;
import com.example.usj.tuopinin.model.entities.OnRegisterListener;
import com.example.usj.tuopinin.model.entities.User;
import com.example.usj.tuopinin.model.entities.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UserData implements UserDataProvider {

    private DaoSession daoSession;

    public UserData(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public void saveUserDetails(String name, String surname, String phoneNumber, String age, String gender, long id, OnFinishedInterfaceListener onFinishedInterfaceListener) {
        List<User> users = daoSession.getUserDao().loadAll();

        if (users != null && users.size() > 0 && users.get(0) != null) {
            for (User user : users) {
                if (user.getId() == id) {
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPhoneNumber(phoneNumber);
                    user.setAge(age);
                    user.setGender(gender);
                    daoSession.getUserDao().update(user);
                    onFinishedInterfaceListener.onSuccess();
                }
            }
        } else {
            onFinishedInterfaceListener.onError();
        }
    }

    @Override
    public void registerUserCredentials(String username, String password, OnRegisterListener onRegisterListener) {
        List<User> users = getUserWithSpecificUsername(username);
        if (users != null && users.size() > 0) {
            onRegisterListener.onError();
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            daoSession.getUserDao().insert(user);
            onRegisterListener.onSuccess(getUserWithSpecificUsernameAndPassword(username, password).get(0).getId());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return daoSession.getUserDao().loadAll();
    }

    @Override
    public List<User> getUserWithSpecificUsernameAndPassword(String username, String password) {
        QueryBuilder<User> qb = daoSession.getUserDao().queryBuilder();
        qb.where(UserDao.Properties.Username.eq(username), UserDao.Properties.Password.eq(password));
        return qb.list();
    }

    private List<User> getUserWithSpecificUsername(String username) {
        QueryBuilder<User> qb = daoSession.getUserDao().queryBuilder();
        qb.where(UserDao.Properties.Username.eq(username));
        return qb.list();
    }
}
