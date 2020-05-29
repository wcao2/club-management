package com.ascending.training.club.repository;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Club;
import com.ascending.training.club.respository.ClubDao;
import com.ascending.training.club.respository.ClubDaoImpl;
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
public class ClubDaoTest {
    @Autowired
    private ClubDao clubDao;
    private Club c1;

    @Before
    public void init(){
        // just for save
        c1=new Club();
        c1.setLocation("Spain");
        c1.setName("Spanish");
        //c1.setStartDate();
        c1.setDescription("Chinese player");
        clubDao.save(c1);

        System.out.println("=================test started==========================");
    }

    @After
    public void tearDown(){
        Club club=clubDao.getClubByName("Spanish");
        clubDao.delete(club.getId());
        System.out.println("=================test finished==========================");
    }

    /*@Test
    public void saveTest(){//PASS
        Club c=clubDao.save(c1);
        Assert.assertEquals("Spanish",c.getName());
    }*/

    @Test
    public void getClubByNameTest(){//PASS
        Club club=clubDao.getClubByName(c1.getName());
        String name=club.getName();
        Assert.assertEquals(c1.getName(),name);
    }

    @Test
    public void getClubByIdTest(){//PASS
        Club club=clubDao.getClubById(c1.getId());
        String name=club.getName();
        Assert.assertEquals(club.getName(),name);
    }

    @Test
    public void getClubEagerTest(){//PASS
        List<Club> clubs = clubDao.getClubsEager();
        int expectNum=4;
        Assert.assertEquals(expectNum,clubs.size());
    }

    /*@Test
    public void deleteClubTest(){//PASS
        boolean b=clubDao.delete(3L);
        Assert.assertTrue(b);
    }*/

    @Test
    public void updateClubDescTest(){
        Club club=clubDao.getClubByName(c1.getName());
        club.setDescription("this is a awesome club");
        boolean bool=clubDao.updateDesc(club);
        Assert.assertTrue(bool);
    }

}


