package com.ascending.training.club.repository;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Image;
import com.ascending.training.club.model.Role;
import com.ascending.training.club.model.User;
import com.ascending.training.club.respository.ImageDao;
import com.ascending.training.club.respository.RoleDao;
import com.ascending.training.club.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class ImageDaoTest {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleDao roleDao;

    private User testuser=null;

    @Before
    public void init(){
        testuser=new User();
        testuser.setName("Danny");
        testuser.setEmail("d@gmu.edu");
        List<Role> roles=new ArrayList<>();
        roles.add(roleDao.getById(2L));
        testuser.setRoles(roles);
        userService.saveUser(testuser);
    }

    @After
    public void tearDown(){

        userService.deleteUser(testuser.getId());
    }

    /*@Test
    public void saveTest(){
        Image img=new Image(testuser,"1.txt","asdasdasd", LocalDateTime.now());
        Image image = imageDao.save(img);
        Assert.assertEquals("asdasdasd",image.getS3Key());
    }*/

    @Test
    public void getImgByUserIdTest(){
        List<Image> images=imageDao.getByUserId(testuser.getId());
        Assert.assertEquals(0,images.size());
    }

    @Test
    public void delImgByUserIdTest(){
        int i = imageDao.delByUserId(testuser.getId());
        Assert.assertEquals(0,i);
    }
}
