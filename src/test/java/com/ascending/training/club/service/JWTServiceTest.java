package com.ascending.training.club.service;


import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class JWTServiceTest {
    @Autowired
    private JWTService jwtService;

    @Test
    public void generateTokenTest(){
        User u= new User();
        u.setId(1L);
        u.setName("dwang");
        String token=jwtService.generateToken(u);
        Assert.assertNotNull(token);

    }
}
