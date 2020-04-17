package com.ascending.training.club.repository;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.User;
import com.ascending.training.club.respository.UserDao;
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
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Before
    public void init(){
        System.out.println("=================test started==========================");
    }
    @After
    public void tearDown(){
        //clubDao.delete(c1); for save
        System.out.println("=================test finished==========================");
    }

    @Test
    public void getUserByCredentialsTest() throws Exception{
        String email="xyhuang@training.ascendingdc.com";
        String password="25f9e794323b453885f5181f1b624d0b";
        User u = userDao.getUserByCredentials(email,password);
        Assert.assertEquals(u.getEmail(),"xyhuang@training.ascendingdc.com");
    }

    @Test
    public void getUserByIdTest(){
        User u=userDao.getUserById(1L);
        Assert.assertEquals("dwang",u.getName());
    }
}
