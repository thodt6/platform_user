/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author THOND
 */
@SpringBootApplication(scanBasePackages = "com.unibro")
public class Application {

    public static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        SpringApplication.run(Application.class, args);
    }
}
