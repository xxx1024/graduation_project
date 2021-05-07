package com.xxx.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xxx.dao.AdminDao;
import com.xxx.pojo.Admin;
import com.xxx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public Admin login(String username, String password) {
        return adminDao.login(username, password);
    }
}
