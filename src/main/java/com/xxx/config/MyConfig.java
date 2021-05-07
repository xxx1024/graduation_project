package com.xxx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("sign-in");
        registry.addViewController("/login").setViewName("sign-in");
        registry.addViewController("/register").setViewName("sign-up");
        registry.addViewController("/main").setViewName("user/index");
        registry.addViewController("/add").setViewName("user/add");
        registry.addViewController("/show").setViewName("user/show");
        registry.addViewController("/update").setViewName("user/update");
        registry.addViewController("/user").setViewName("user/user");
        registry.addViewController("/user-update").setViewName("user/user-update");
        registry.addViewController("/user-avatar").setViewName("user/avatar");
        registry.addViewController("/forget").setViewName("forget");
        registry.addViewController("/pwd-update").setViewName("pwd-update");

    }

    @Bean
    public LocaleResolver localeResolver() {
        return new LanguageResolver();
    }
}