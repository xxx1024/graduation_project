package com.xxx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.pojo.Admin;
import com.xxx.pojo.Register;
import com.xxx.pojo.User;
import com.xxx.service.AdminService;
import com.xxx.service.RegisterService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    //登录
    @RequestMapping("/admin/sign_in")
    public String login(String username, String password, RedirectAttributes ra,
                        HttpSession session){
        Admin login = adminService.login(username, password);
        if (login!=null){
           session.setAttribute("username",username);
            return "redirect:/admin-index";
        }else {
            ra.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin-login";
        }
    }
    //注销
    //注销
    @RequestMapping("/admin/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/sign_in";
    }

    @RequestMapping("/admin-user-show")
    public String qureyAll(Model model, @RequestParam(required = false,defaultValue="1",value="pageNum")
            Integer pageNum, @RequestParam(defaultValue="5",value="pageSize")Integer pageSize){
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
            List<User> lists = adminService.queryAllUser();
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<User> pageInfo = new PageInfo<User>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/user-show";
    }
    //根据id查询
    @RequestMapping("/admin-queryUserId")
    public String qureyById(int id, Model model){
        User user = adminService.queryByUserId(id);
        model.addAttribute("lists",user);
        return "admin/user-update";
    }
    //查询捐赠详情
    @Autowired
    private RegisterService registerService;
    @RequestMapping("/admin-user-items")
    public String UserItems(int id,Model model, @RequestParam(required = false,defaultValue="1",value="pageNum")
            Integer pageNum, @RequestParam(defaultValue="5",value="pageSize")Integer pageSize){
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
            List<Register> lists = registerService.queryAll(id);
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Register> pageInfo = new PageInfo<Register>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("id",id);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/user-items";
    }
    //修改
    @RequestMapping("/admin/user-update")
    public String update(User user){
        adminService.updateUser(user);
        return "redirect:/admin-user-show";
    }
    //删除
    @RequestMapping("/admin/user-delete")
    public String delete(int id){
        adminService.deleteUser(id);
        return "redirect:/admin-user-show";
    }


    @RequestMapping("/admin-items-show")
    public String qureyAllItems(HttpSession session,Model model,
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
            List<Register> lists = adminService.queryAllItems();
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Register> pageInfo = new PageInfo<Register>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/items-show";
    }
    //根据id查询
    @RequestMapping("/admin-queryItemsId")
    public String queryItemsId(int id, Model model){
        Register register = adminService.queryByItemsId(id);
        model.addAttribute("lists",register);
        return "admin/items-update";
    }
    //修改
    @RequestMapping("/admin/items-update")
    public String updateItems(Register register){
        adminService.updateItems(register);
        return "redirect:/admin-items-show";
    }
    //删除
    @RequestMapping("/admin/items-delete")
    public String deleteItems(int id){
        adminService.deleteItems(id);
        return "redirect:/admin-items-show";
    }


    @RequestMapping("/admin-show")
    public String qureyAllAdmin(HttpSession session,Model model,
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
            List<Admin> lists = adminService.queryAllAdmin();
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Admin> pageInfo = new PageInfo<Admin>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/admin-show";
    }
    //根据id查询
    @RequestMapping("/admin-queryAdminId")
    public String queryAdminId(int id, Model model){
        Admin admin = adminService.queryByAdminId(id);
        model.addAttribute("lists",admin);
        return "admin/admin-update";
    }
    //修改
    @RequestMapping("/admin/admin-update")
    public String updateAdmin(Admin admin){
        adminService.updateAdmin(admin);
        return "redirect:/admin-show";
    }
    //删除
    @RequestMapping("/admin/admin-delete")
    public String deleteAdmin(int id){
        adminService.deleteAdmin(id);
        return "redirect:/admin-show";
    }
    //新增
    @RequestMapping("/admin/admin-add")
    public String addAdmin(Admin admin){
        adminService.add(admin);
        return "redirect:/admin-show";
    }

    //统计物品数量
    @RequestMapping("/admin-index")
    public String ItemsNum(Model model){
        String item1 ="医用口罩";
        String item2 ="医用防护服";
        String item3 ="一次性医用手套";
        String item4 ="其他";
        int number1 = adminService.ItemsNum(item1);
        int number2 = adminService.ItemsNum(item2);
        int number3 = adminService.ItemsNum(item3);
        int number4 = adminService.ItemsNum(item4);
        model.addAttribute("number1",number1);
        model.addAttribute("number2",number2);
        model.addAttribute("number3",number3);
        model.addAttribute("number4",number4);
        return "admin/index";
    }

    //搜索用户
    @RequestMapping("/searchUser")
    public String qureyName(Model model,String username,
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
            List<User> lists = adminService.searchUser(username);
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<User> pageInfo = new PageInfo<User>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("username",username);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/user-show";
    }

    //搜索物品
    @RequestMapping("/searchItems")
    public String searchItems(String items,Model model,
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
            List<Register> lists = adminService.searchItems(items);
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Register> pageInfo = new PageInfo<Register>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("items",items);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/items-show";
    }

    //搜索admin
    @RequestMapping("/searchAdmin")
    public String searchAdmin(String username,Model model,
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
            List<Admin> lists = adminService.searchAdmin(username);
            System.out.println("分页数据："+lists);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Admin> pageInfo = new PageInfo<Admin>(lists,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("username",username);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "admin/admin-show";
    }
}
