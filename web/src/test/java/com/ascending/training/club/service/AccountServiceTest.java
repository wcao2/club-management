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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PlayerService playerService;

    private Player player=null;
    private Account account=null;

    @Before
    public void init(){
        System.out.println("========================start test===============================");
        account=new Account("Bank of England","Credit",new BigDecimal(9999.99));
        player=playerService.getPlayerByName("Mullier");
        account.setPlayer(player);
        accountService.save(account);
    }

    @After
    public void tearDown(){
        accountService.deleteAccountById(account.getId());
        System.out.println("========================finish test===============================");
    }

    /*@Test
    public void saveTest(){
        Account account=new Account("Bank of England","Credit",new BigDecimal(9999.99));
        Player player=playerService.getPlayerByName("Mullier");
        account.setPlayer(player);
        Account a = accountService.save(account);
        Assert.assertEquals("Bank of England",a.getBankName());
    }*/

    @Test
    public void getAccountByIdTest(){
        Account a=accountService.getAccountById(account.getId());
        Assert.assertEquals(account.getBankName(),a.getBankName());
    }

   /* @Test
    public void deleteAccountByIdTest(){
        boolean bool=accountService.deleteAccountById(account.getId());
        Assert.assertTrue(bool);
    }*/

    @Test
    public void getBalanceOnlyTest(){
        List<BigDecimal> balanceAll = accountService.getBalanceOnly();
        Assert.assertEquals(5,balanceAll.size());
    }
}
