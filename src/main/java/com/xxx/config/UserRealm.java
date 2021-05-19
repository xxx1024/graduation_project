package com.xxx.config;

import com.xxx.pojo.Admin;
import com.xxx.pojo.User;
import com.xxx.service.AdminService;
import com.xxx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "UserRealm";
    }

    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("user认证");

        UsernamePasswordToken userToken =(UsernamePasswordToken) token;
        User user = userService.queryAll(userToken.getUsername());
        if (user==null){
            return null;
        }else {
            return new SimpleAuthenticationInfo("",user.getPassword(),"");
        }

   }
}
