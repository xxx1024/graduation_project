package com.xxx.dao;

import com.xxx.pojo.Admin;
import com.xxx.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    //注册
    void register(User user);
    //查询name
    User queryAll(String username);
    //name登录
    User login(@Param("username") String username, @Param("password") String password);
    //email登录
    User loginEmail(@Param("username") String username, @Param("password") String password);
    //修改密码
    void update(User user);
    //修改name
    void updateName(User user);
    //修改头像
    void updateImg(Integer id, String avatar);
    //查询email
    User queryEmail(String email);
    //统计物品数量
    int ItemsNum(String items,Integer id);

}
