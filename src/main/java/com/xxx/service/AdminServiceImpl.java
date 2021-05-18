package com.xxx.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xxx.dao.AdminDao;
import com.xxx.pojo.Admin;
import com.xxx.pojo.Register;
import com.xxx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public Admin login(String username, String password) {
        return adminDao.login(username, password);
    }

    @Override
    public List<User> queryAllUser() {
        return adminDao.queryAllUser();
    }

    @Override
    public User queryByUserId(int id) {
        return adminDao.queryByUserId(id);
    }

    @Override
    public void updateUser(User user) {
        adminDao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        adminDao.deleteUser(id);
    }

    @Override
    public List<Register> queryAllItems() {
        return adminDao.queryAllItems();
    }

    @Override
    public Register queryByItemsId(int id) {
        return adminDao.queryByItemsId(id);
    }

    @Override
    public void updateItems(Register register) {
            adminDao.updateItems(register);
    }

    @Override
    public void deleteItems(int id) {
            adminDao.deleteItems(id);
    }

    @Override
    public void add(Admin admin) {
        adminDao.add(admin);
    }

    @Override
    public List<Admin> queryAllAdmin() {
        return adminDao.queryAllAdmin();
    }

    @Override
    public Admin queryByAdminId(int id) {
        return adminDao.queryByAdminId(id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminDao.updateAdmin(admin);
    }

    @Override
    public void deleteAdmin(int id) {
        adminDao.deleteAdmin(id);
    }

    @Override
    public int ItemsNum(String items) {
        return adminDao.ItemsNum(items);
    }

    @Override
    public List<User> searchUser(String username) {
        return adminDao.searchUser(username);
    }

    @Override
    public List<Register> searchItems(String items) {
        return adminDao.searchItems(items);
    }

    @Override
    public List<Admin> searchAdmin(String username) {
        return adminDao.searchAdmin(username);
    }

}
