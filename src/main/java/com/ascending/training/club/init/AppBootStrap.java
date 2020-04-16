package com.ascending.training.club.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.ascending.training.club"})
@ServletComponentScan(basePackages = {"com.ascending.training.club.filter"})
public class AppBootStrap {//extends SpringBootServletInitializer
    public static void main(String[] args) {
        // equals to AppBootStrap app=new AppBootStrap();
        SpringApplication.run(AppBootStrap.class,args);
    }
}
