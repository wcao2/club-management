package com.ascending.training.club.repository;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Account;
import com.ascending.training.club.model.Player;
import com.ascending.training.club.respository.AccountDao;
import com.ascending.training.club.respository.AccountDaoImpl;
import com.ascending.training.club.respository.PlayerDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class AccountDaoTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PlayerDao playerDao;

    @Before
    public void init(){
        //accountDao=new AccountDaoImpl();
        System.out.println("=================test started==========================");
    }

    @After
    public void tearDown(){
        System.out.println("=================test finished==========================");
    }


    @Test
    public void saveTest(){
        //Long id, String bankName, String accountType, BigDecimal balance
        Account account=new Account("BANK OF CHINA",new String("credit"),new BigDecimal(9999.9));
        Player player = playerDao.getPlayerByName("Mullier");
        account.setPlayer(player);
        Account a1=accountDao.save(account);
        Assert.assertEquals("BANK OF CHINA",a1.getBankName());
    }

    @Test
    public void getAccountByIdTest(){
        Account account = accountDao.getAccountById(7L);
        Assert.assertEquals("BANK OF CHINA",account.getBankName());
    }

    @Test
    public void deleteTest(){
        boolean bool = accountDao.delete(8L);
        Assert.assertTrue(bool);
    }

    @Test
    public void getAccount(){
        List<Account> account=accountDao.getAll();
        Assert.assertEquals(1,account.size());
    }
}
