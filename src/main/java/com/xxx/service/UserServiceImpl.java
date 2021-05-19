package com.xxx.service;

import com.xxx.dao.UserDao;
import com.xxx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public User queryAll(String username) {
        return userDao.queryAll(username);
    }


    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public User loginEmail(String username, String password) {
        return userDao.loginEmail(username,password);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void updateName(User user) {
        userDao.updateName(user);
    }

    @Override
    public void updateImg(Integer id, String avatar) {
        userDao.updateImg(id,avatar);
    }

    @Override
    public User queryEmail(String email) {
        return userDao.queryEmail(email);
    }

    @Override
    public int ItemsNum(String items, Integer id) {
        return userDao.ItemsNum(items, id);
    }


}
