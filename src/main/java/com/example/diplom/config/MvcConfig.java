//package com.example.diplom.config;
//
//import com.example.diplom.view.auth.loginPage;
//import com.vaadin.flow.spring.security.VaadinWebSecurity;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Description;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//
//@Configuration
//public class MvcConfig extends VaadinWebSecurity {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        setLoginView(http, loginPage.class);
//    }
//}