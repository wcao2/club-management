package com.ascending.training.club.service;


import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Role;
import com.ascending.training.club.model.User;
import com.ascending.training.club.respository.RoleDao;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class JWTServiceTest {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserService userService;

    @Test
    public void generateTokenTest(){
        User u = userService.getUserById(1L);
        List<Role> roles=new ArrayList<>();
        Role r=roleDao.getById(1L);
        roles.add(r);
        u.setRoles(roles);

        String token=jwtService.generateToken(u);
        Assert.assertNotNull(token);

    }

    @Test
    public void decryptTokenTest(){
        User u = userService.getUserById(1L);
        String token=jwtService.generateToken(u);
        Claims c=jwtService.decpytToken(token);
        String username=c.getSubject();
        Assert.assertEquals(u.getName(),username);
    }
}
