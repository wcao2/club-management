package com.ascending.training.club.controller;

import com.ascending.training.club.model.Account;
import com.ascending.training.club.model.Player;
import com.ascending.training.club.service.AccountService;
import com.ascending.training.club.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value={"/accounts"})
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PlayerService playerService;

//    Account save(Account account);
//    boolean delete(Long id);
//    Account getAccountById(Long id);

    //{prefix}/accounts POST
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Account createAccount(@RequestParam("playerName") String playerName,@RequestBody Account account){
        Player player=playerService.getPlayerByName(playerName);
        account.setPlayer(player);
        return accountService.save(account);
    }

    //{prefix}/accounts DELETE
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public String deleteAccount(@PathVariable("id") Long id){
        String msg=null;
        boolean isSuccess=accountService.deleteAccountById(id);
        if(isSuccess){
            msg="the account is successfully deleted";
        }else {
            msg="failure to delete account";
        }
        return msg;
    }

    //{prefix}/accounts GET
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") Long id){
        Account account=accountService.getAccountById(id);
        return account;
    }
    //{prefix}/accounts GET
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<BigDecimal> getBalanceAll(){
        List<BigDecimal> balanceAll = accountService.getBalanceAll();
        return balanceAll;
    }
}
