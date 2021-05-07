package com.xxx.dao;

import com.xxx.pojo.Register;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegisterDao {
    //新增表单
    void add(Register register);
    //查询表单
    List<Register> queryAll(int id);
    //根据id查询
    Register queryById(int id);
    //修改
    void update(Register register);
    //删除表单
    void delete(int id);
    //搜索
    List<Register> queryName(@Param("userid") Integer id,@Param("items") String items);
}
