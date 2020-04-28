package com.ascending.training.club.service;

import com.ascending.training.club.model.Account;
import com.ascending.training.club.respository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    public AccountDao accountDao;

    public Account getAccountById(Long id){return accountDao.getAccountById(id);}
    public Account save(Account account){return accountDao.save(account);}
    public boolean deleteAccountById(Long id){return accountDao.delete(id);}
    public List<BigDecimal> getBalanceAll(){
        List<Account> allAccounts = accountDao.getAll();
        List<BigDecimal> balance = allAccounts.stream().map(a -> a.getBalance()).collect(Collectors.toList());
        return balance;
    }
}
