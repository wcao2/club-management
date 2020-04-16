package com.ascending.training.club.repository;


import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Role;
import com.ascending.training.club.respository.RoleDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class RoleDaoTest {
    @Autowired
    private RoleDao roleDao;

    @Before
    public void init(){
        System.out.println("=============================== test started===============================");
    }

    @After
    public void tearDown(){
        System.out.println("================================finished test===============================");
    }

    @Test  //pass
    public void saveRoleTest(){
        Role r=new Role("normal","A",true,false,false,false);
        Role role=roleDao.save(r);
        Assert.assertEquals("normal",r.getName());
    }

    @Test  //pass
    public void getRolesTest(){
        List<Role> roles=roleDao.getRoles();
        int expectedNum=4;
        Assert.assertEquals(expectedNum,roles.size());
    }

    @Test    //pass
    public void getRoleByIdTest(){
        Role role=roleDao.getById(2L);
        Assert.assertNotNull(role);
        Assert.assertEquals(2L, role.getId().longValue());
    }

    @Test   //pass
    public void deleteByIdTest(){
        int num=roleDao.deleteById(4L);
        Assert.assertEquals(1,num);
    }

    @Test   //pass
    public void updateRoleTest(){
        Role role=roleDao.getById(4L);
        role.setAllowedResource("c://file system/ascending/my programs");
        int num=roleDao.update(role);
        Assert.assertEquals(1,num);
    }

}
