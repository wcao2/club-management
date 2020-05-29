package com.ascending.training.club.repository;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Player;
import com.ascending.training.club.respository.PlayerDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class PlayerDaoTest {
    @Autowired
    private PlayerDao playerDao;

    private Player p =null;
    @Before
    public void init(){
        System.out.println("=================test started==========================");
        Player player=new Player("WuLei","Lei@gmu.edu","9871_MOON_ROAD", LocalDate.now(),"audience");
        p = playerDao.save(player, "Bayern Munich");
    }
    @After
    public void tearDown(){
        playerDao.delete(p.getId());
        System.out.println("=================test finished==========================");
    }

   /* @Test
    public void saveTest(){ //PASS
        Player player=new Player(3L,"WuLei","Lei@gmu.edu","9871_MOON_ROAD", LocalDate.now(),"audience");
        Player p=playerDao.save(player,"Bayern Munich");
        Assert.assertEquals("WuLei",p.getName());
    }*/

    @Test
    public void updatePlayerEmailTest(){//PASS
        Player player = playerDao.getPlayerByName(p.getName());
        player.setEmail("test@gmu.edu");
        Player updatePlayer=playerDao.updatePlayerEmail(player);
        Assert.assertEquals(updatePlayer.getName(),p.getName());
    }

    @Test
    public void getPlayerByNameTest(){ //PASS
        Player p1 = playerDao.getPlayerByName(p.getName());
        Assert.assertEquals(p1.getName(),p.getName());
    }

   /* @Test
    public void deletePlayerTest(){//PASS
        Player p = playerDao.getPlayerByName("WuLei");
        boolean a=playerDao.delete(p.getId());
        Assert.assertTrue(a);
    }*/

    @Test
    public void getPlayersAndClubTest(){//PASS
        List<Player> players = playerDao.getPlayersAndClub();
        Assert.assertEquals(3,players.size());
    }

    @Test
    public void getPlayerByIdTest(){//PASS
        Player player = playerDao.getPlayerById(p.getId());
        Assert.assertEquals(p.getName(),player.getName());
    }
}
