package com.xxx.controller;

import com.xxx.pojo.User;
import com.xxx.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    JavaMailSenderImpl mailSender;
    //生成验证码
    String emailServiceCode = UUID.randomUUID().toString().substring(0, 6);
    //发送邮件
    @ResponseBody
    @RequestMapping("/email/code")
    public void sendEmail(String toMail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //验证码
        mailMessage.setSubject("验证码");
        mailMessage.setText("验证码为:"+emailServiceCode);
        mailMessage.setFrom(from);
        mailMessage.setTo(toMail);
        mailSender.send(mailMessage);
    }

    //注册
    @RequestMapping("/user/register")
    public String register(String username,String email,
                           String code,String password,
                           User user, RedirectAttributes ra){
        User user1 = userService.queryAll(username);
        User user2 = userService.queryEmail(email);
        if (user1!=null){
            ra.addFlashAttribute("msg","用户名已存在");
            return "redirect:/register";
        }else if (user2!=null){
            ra.addFlashAttribute("msg","邮箱已注册");
            return "redirect:/register";
        } else {
            if (!emailServiceCode.equals(code)){
                ra.addFlashAttribute("msg","验证码错误");
                return "redirect:/register";
            }else {
                userService.register(user);
                return "redirect:/login";
            }

        }

    }
    //忘记密码
    @RequestMapping("/user/password")
    public String password(HttpSession session,String email, String code,RedirectAttributes ra){
        User user = userService.queryEmail(email);
        //存入session修改密码用
        session.setAttribute("id",user.getId());
        if (user!=null){
            if (!emailServiceCode.equals(code)){
                ra.addFlashAttribute("msg","验证码错误");
                return "redirect:/forget";
            }else {
                return "redirect:/pwd-update";
            }
        }else {
            ra.addFlashAttribute("msg","邮箱账号不存在");
            return "redirect:/forget";
        }
    }
    //修改密码
    @RequestMapping("/user/pwd-update")
    public String updatepwd(User user,String password,
                            String password1,RedirectAttributes ra){
        if (password.equals(password1)){
            userService.update(user);
            return "redirect:/login";
        }else {
            ra.addFlashAttribute("msg","两次输入密码不一致");
            return "redirect:/pwd-update";
        }
    }
    //登录
    @RequestMapping("/user/login")
    public String login(String username, String password, RedirectAttributes ra1,
                        HttpSession session){

        Subject subject = SecurityUtils.getSubject();
        //封装登录用户的用户名和密码做成UsernameToken,拿到令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        //执行登录方法，经过一系列跳转，然后到UserRealm类的doGetAuthenticationInfo()方法，
        // 用户名和密码做认证，如果没有异常就认证成功跳转页面，有异常的话，
        // 走shiro底层quickStart的异常登录
        try {
            subject.login(usernamePasswordToken);
            User user = userService.queryAll(username);
            //存入name、id、email
            session.setAttribute("username",user.getUsername());
            session.setAttribute("userid",user.getId());
            session.setAttribute("useremail",user.getEmail());
            session.setAttribute("avatar",user.getAvatar());
            return "redirect:/main";//登录成功跳转到main界面
        } catch (IncorrectCredentialsException e) {
            ra1.addFlashAttribute("msg","密码错误"); //用户名不存在，还是返回Login页
            return "redirect:/login";
        }catch (UnknownAccountException e){
            ra1.addFlashAttribute("msg","用户名错误");//密码不存在，还是返回Login页
            return "redirect:/login";
        }

        /*User login = userService.login(username, password);
        User user1 = userService.loginEmail(username, password);
        if (login!=null||user1!=null){
            //System.out.println("session存入:"+username);
            User user = userService.queryAll(username);
            //System.out.println("用户id："+user.getId());
            //存入name、id、email
            session.setAttribute("username",user.getUsername());
            session.setAttribute("userid",user.getId());
            session.setAttribute("useremail",user.getEmail());
            session.setAttribute("avatar",user.getAvatar());
            return "redirect:/main";
        }else {
            ra1.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/login";
        }*/
    }
    //注销
    @RequestMapping("/user/logout")
    public String logout(HttpSession session){
//        session.invalidate();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/login";
    }
    //修改name
    @RequestMapping("/user/updatename")
    public String update(HttpSession session,User user,String username,RedirectAttributes ra){
            User user1 = userService.queryAll(username);
            if (username==null){
                ra.addFlashAttribute("error","用户名不能为空");
                return "redirect:/user-update";
            }
            else if (user1!=null){
                ra.addFlashAttribute("error","用户名已存在");
                return "redirect:/user-update";
            }else {
                userService.updateName(user);
                session.setAttribute("username",username);
                ra.addFlashAttribute("success","修改成功！");
                return "redirect:/user-update";
        }
        }
        //修改密码
    @RequestMapping("/user/update")
    public String update(User user,String password,String password1,RedirectAttributes ra){

        if (password==null||password1==null){
            ra.addFlashAttribute("error1","密码不能为空");
            return "redirect:/user-update";
        }
        else if (password.equals(password1)){
            userService.update(user);
            ra.addFlashAttribute("success1","修改成功！");
            return "redirect:/user-update";
        }else {
            ra.addFlashAttribute("error1","两次输入密码不一致");
            return "redirect:/user-update";
        }

    }

    //上传头像文件时的最大大小,使用字节为单位(从配置文件读取)
    @Value("${project.avatar-max-size}")
    private int avatarMaxSize;
     //上传头像时允许的图片类型(从配置文件中读取)
    @Value("${project.avatar-types}")
    private List<String> avatarTypes;

    //修改头像
    @RequestMapping("/user/avatar")
    public String changeAvatar(@RequestParam("file") MultipartFile file, HttpSession session, RedirectAttributes ra){
        // 判断上传文件是否为空
        boolean isEmpty = file.isEmpty();
        // 上传文件的大小(SpringBoot框架默认限制了上传文件的大小)
        long size = file.getSize();
        // 上传文件的类型
        String contentType = file.getContentType();
        if(isEmpty) {
            ra.addFlashAttribute("error","上传文件失败！请选择有效的头像文件！");
            return "redirect:/user-avatar";
        }else if(size > avatarMaxSize) {
            ra.addFlashAttribute("error","上传文件失败，不允许上传超过"+(avatarMaxSize/1024)+"KB大小的图片文件");
            return "redirect:/user-avatar";
        }
        else if(!avatarTypes.contains(contentType)) {
            ra.addFlashAttribute("error","上传头像失败，只允许上传如下格式:\n\n"+ avatarTypes);
            return "redirect:/user-avatar";
        }
        // 创建保存头像文件的目录（需要的话也可以写在properties配置文件中）
        String dirName = "upload";
        // 获取webapp下的某个文件夹的真实路径,"upload"是要创建的子目录名称
        String path = Thread.currentThread().
                getContextClassLoader().getResource("static").
                getPath()+"/upload";
//        String path =System.getProperty("user.dir")+
//                "\\src\\main\\resources\\static\\upload";
        System.out.println("保存路径:"+path);
        // 目标文件夹
        File parent = new File(path);
        if(!parent.exists()) {
            System.out.println("创建文件夹");
            parent.mkdirs();
        }

        // 上传的文件保存的文件名(用当前时间表示，防止重复)
        String filename = ""+ UUID.randomUUID();
        // 上传的文件的原始名
        String originalFilename = file.getOriginalFilename();
        // 上传的文件保存的后缀名
        /* 如果原文件名中没有小数点，则返回-1，在这种情况下，还调用substring截取，就会出现StringIndexOutOfBoundsException
   		 如果原文件名中只有1个小数点，且是文件名的第1个字符，这样的命名方式其实是表示Linux系统中的隐藏文件，且substring是不合理的
   		 可能需要进行 if (beginIndex > 0) 的判断
   		 (以上判断因为在上面对上传文件的类型做了处理，所以得到的都是正确的文件格式，以上判断就不需要了)
   		 */

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 文件名称
        String child = filename + suffix;

        // 上传的文件保存的路径及名字
        File dest = new File(parent, child);
        // 执行保存文件
        try {
            file.transferTo(dest);
            System.out.println("保存文件"+dest);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 将上传的文件路径保存到数据库中
        Integer id = Integer.valueOf(session.getAttribute("userid").toString());
        String avatar = "/"+dirName +"/" + child;
        userService.updateImg(id, avatar);

        session.setAttribute("avatar",avatar);
        System.out.println("session保存地址："+avatar);
        ra.addFlashAttribute("success","上传头像成功");
        // 响应成功与头像路径
        return "redirect:/user-avatar";
    }
}
