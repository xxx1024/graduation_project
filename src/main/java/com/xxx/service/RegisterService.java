package com.xxx.service;

import com.xxx.pojo.Register;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegisterService {
    //新增
    void add(Register register);
    //查询
    List<Register> queryAll(int id);
    //根据id查询
    Register queryById(int id);
    //更新
    void update(Register register);
    //删除
    void delete(int id);
    //搜索
    List<Register> queryName(Integer id,String items);

}
