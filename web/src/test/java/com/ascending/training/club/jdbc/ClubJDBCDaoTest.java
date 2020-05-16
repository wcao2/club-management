package com.ascending.training.club.jdbc;

import com.ascending.training.club.model.ClubJDBC;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ClubJDBCDaoTest {

    private ClubJDBCDao clubDao;

    @Before
    public void init(){clubDao=new ClubJDBCDao();}
    @After
    public void tearDown(){

    }

    @Test
    public void getClubsTest(){
        List<ClubJDBC> clubs=clubDao.getClubs();
        int expectNum=2;

        Assert.assertEquals(expectNum,clubs.size());
    }
}
