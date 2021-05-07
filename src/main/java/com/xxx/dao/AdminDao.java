package com.xxx.dao;

import com.xxx.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminDao {
    //登录
    Admin login(@Param("username") String username, @Param("password") String password);

}
