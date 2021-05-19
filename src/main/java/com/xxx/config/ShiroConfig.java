package com.xxx.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){

    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    //设置安全管理器
    bean.setSecurityManager(securityManager);
    //添加shiro内置过滤器
         /*   anon:无需认证
            authc:必须认证才能访问
            user:有记住我功能才能访问
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问*/


        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/","anon");
        filterMap.put("/login","anon");
        filterMap.put("/register","anon");
        filterMap.put("/forget","anon");
        filterMap.put("/pwd-update","anon");

        filterMap.put("/main","authc");
        filterMap.put("/add","authc");
        filterMap.put("/show","authc");
        filterMap.put("/update","authc");
        filterMap.put("/user","authc");
        filterMap.put("/user-update","authc");
        filterMap.put("/user-avatar","authc");

        bean.setFilterChainDefinitionMap(filterMap);

        bean.setLoginUrl("/login");

        return bean;
    }

    //2.
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(userRealm);
        return securityManager;
    }


    //1.创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
