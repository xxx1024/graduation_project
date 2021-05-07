package com.xxx.service;

import com.xxx.dao.RegisterDao;
import com.xxx.pojo.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private RegisterDao registerDao;
    @Override
    public void add(Register register) {
        registerDao.add(register);
    }

    @Override
    public List<Register> queryAll(int id) {
        return registerDao.queryAll(id);
    }
    @Override
    public Register queryById(int id) {
        return registerDao.queryById(id);
    }

    @Override
    public void update(Register register) {
        registerDao.update(register);
    }

    @Override
    public void delete(int id) {
        registerDao.delete(id);
    }

    @Override
    public List<Register> queryName(Integer id, String items) {
        return registerDao.queryName(id, items);
    }
}
