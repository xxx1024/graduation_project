package com.xxx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.pojo.Register;
import com.xxx.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    //新增
    @RequestMapping("/items/add")
    public String add(HttpSession session,Register register){
        String username = (String) session.getAttribute("username");
        System.out.println("用户名："+username);
        registerService.add(register);
        return "redirect:/show";
    }
    //查看
    @RequestMapping("/show")
    public String qureyAll(HttpSession session,Model model,
            @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
            @RequestParam(defaultValue="5",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            int userid = (int) session.getAttribute("userid");
            System.out.println("用户id："+userid);
            List<Register> lists = registerService.queryAll(userid);
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Register> pageInfo = new PageInfo<Register>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "user/show";
    }
    //根据id查询
    @RequestMapping("/query")
    public String qureyById(int id, Model model){
        Register query = registerService.queryById(id);
        model.addAttribute("lists",query);
        return "user/update";
    }
    //修改
    @RequestMapping("/items/update")
    public String update(Register register){
        registerService.update(register);
        return "redirect:/show";
    }
    //删除
    @RequestMapping("/items/delete")
    public String delete(int id){
        registerService.delete(id);
        return "redirect:/show";
    }

    //搜索
    @RequestMapping("/search")
    public String qureyName(HttpSession session,Model model,String items,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="5",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;    //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            Integer userid = Integer.valueOf(session.getAttribute("userid").toString());

            List<Register> lists = registerService.queryName(userid,items);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Register> pageInfo = new PageInfo<Register>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("items",items);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "user/show";
    }
}
