package com.xxx.service;

import com.xxx.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //注册
    void register(User user);
    //查询
    User queryAll(String username);
    //登录
    User login(String username,String password);
    //Email登录
    User loginEmail(String username,String password);
    //修改密码
    void update(User user);
    //修改name
    void updateName(User user);
    //修改头像
    void updateImg(Integer id, String avatar);
    //查询email
    User queryEmail(String email);
}
