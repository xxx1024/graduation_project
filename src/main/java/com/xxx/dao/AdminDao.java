package com.xxx.dao;

import com.xxx.pojo.Admin;
import com.xxx.pojo.Register;
import com.xxx.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDao {
    //登录
    Admin login(@Param("username") String username, @Param("password") String password);
    //查询用户
    List<User> queryAllUser();
    //根据id查询用户
    User queryByUserId(int id);
    //修改用户
    void updateUser(User user);
    //删除用户
    void deleteUser(int id);


    //查询表单
    List<Register> queryAllItems();
    //根据id查询表单
    Register queryByItemsId(int id);
    //修改表单
    void updateItems(Register register);
    //删除表单
    void deleteItems(int id);

    //新增admin
    void add(Admin admin);
    //查询admin
    List<Admin> queryAllAdmin();
    //根据id查询admin
    Admin queryByAdminId(int id);
    //修改admin
    void updateAdmin(Admin admin);
    //删除admin
    void deleteAdmin(int id);

    //统计物品数量
    int ItemsNum(String items);
    //搜索用户
    List<User> searchUser(String username);
    //搜索物品
    List<Register> searchItems(String items);
    //搜索管理员
    List<Admin> searchAdmin(String username);
}
