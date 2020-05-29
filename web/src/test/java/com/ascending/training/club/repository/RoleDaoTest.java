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

    private Role role=null;

    @Before
    public void init(){
        System.out.println("=============================== test started===============================");
        role=new Role("normal","A",true,false,false,false);
        roleDao.save(role);
    }

    @After
    public void tearDown(){
        roleDao.deleteById(role.getId());
        System.out.println("================================finished test===============================");
    }

  /*  @Test  //pass
    public void saveRoleTest(){
        Role r=new Role("normal","A",true,false,false,false);
        Role role=roleDao.save(r);
        Assert.assertEquals("normal",r.getName());
    }*/

    @Test  //pass
    public void getRolesTest(){
        List<Role> roles=roleDao.getRoles();
        int expectedNum=4;
        Assert.assertEquals(expectedNum,roles.size());
    }

    @Test    //pass
    public void getRoleByIdTest(){
        Role r=roleDao.getById(role.getId());
        Assert.assertNotNull(r);
        Assert.assertEquals(r.getName(), role.getName());
    }

   /* @Test   //pass
    public void deleteByIdTest(){
        int num=roleDao.deleteById(4L);
        Assert.assertEquals(1,num);
    }*/

    @Test   //pass
    public void updateRoleTest(){
        Role r=roleDao.getById(role.getId());
        r.setAllowedResource("c://file system/ascending/my programs");
        int num=roleDao.update(r);
        Assert.assertEquals(1,num);
    }

}
