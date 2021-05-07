package com.xxx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class EmailController {


//
//    @RequestMapping("/email/verification")
//    public String verification(String code,Model model){
//        if (!emailServiceCode.equals(code)){
//            model.addAttribute("msg","验证码错误!");
//            return "sign-up";
//        }
//        return "redirect:/login";
//    }

}
