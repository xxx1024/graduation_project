package com.xxx;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@SpringBootTest
class GraduationProjectApplicationTests {

    @Test
    void contextLoads() {

    }
    @Test
    public void test1() throws Exception {

//获取文件的相对路径  可在控制台打印查看输出结果
//        String path1 =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload";
//        System.out.println( path1);
//
//        String path = Thread.currentThread().
//                getContextClassLoader().getResource("static").
//                getPath()+"/upload";
//        System.out.println("保存路径:"+path);
        String emailServiceCode = UUID.randomUUID().toString().substring(0, 6);
        System.out.println(emailServiceCode);
    }

}
