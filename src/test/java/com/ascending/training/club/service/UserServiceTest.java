package com.ascending.training.club.service;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Before
    public void init(){
        System.out.println("========================start test===============================");
    }

    @After
    public void tearDown(){
        System.out.println("========================finish test===============================");
    }

    @Test
    public void getUserByCredentialsTest() throws Exception{
        String email="xyhuang@training.ascendingdc.com";
        String password="25f9e794323b453885f5181f1b624d0b";
        User u = userService.getUserByCredentials(email, password);
        Assert.assertEquals(u.getEmail(),"xyhuang@training.ascendingdc.com");
    }
}
