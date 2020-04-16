package com.ascending.training.club.service;

import com.ascending.training.club.init.AppBootStrap;
import com.ascending.training.club.model.Account;
import com.ascending.training.club.model.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PlayerService playerService;

    @Before
    public void init(){
        System.out.println("========================start test===============================");
    }

    @After
    public void tearDown(){
        System.out.println("========================finish test===============================");
    }

    @Test
    public void saveTest(){
        Account account=new Account("Bank of England","Credit",new BigDecimal(9999.99));
        Player player=playerService.getPlayerByName("Mullier");
        account.setPlayer(player);
        Account a = accountService.save(account);
        Assert.assertEquals("Bank of England",a.getBankName());
    }

    @Test
    public void getAccountByIdTest(){
        Account account=accountService.getAccountById(11L);
        Assert.assertEquals("Bank of England",account.getBankName());
    }

    @Test
    public void deleteAccountByIdTest(){
        boolean bool=accountService.deleteAccountById(11L);
        Assert.assertTrue(bool);
    }
}
