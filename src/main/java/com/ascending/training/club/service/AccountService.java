package com.ascending.training.club.service;

import com.ascending.training.club.model.Account;
import com.ascending.training.club.respository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    public AccountDao accountDao;

    public Account getAccountById(Long id){return accountDao.getAccountById(id);}
    public Account save(Account account){return accountDao.save(account);}
    public boolean deleteAccountById(Long id){return accountDao.delete(id);}
}
