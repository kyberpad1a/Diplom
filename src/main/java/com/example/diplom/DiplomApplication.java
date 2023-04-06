package com.example.diplom;

import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.rmi.RemoteException;
import java.util.Arrays;

import static java.rmi.registry.LocateRegistry.getRegistry;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class DiplomApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DiplomApplication.class, args);
    }



}
