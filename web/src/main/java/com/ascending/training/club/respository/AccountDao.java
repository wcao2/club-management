package com.ascending.training.club.respository;

import com.ascending.training.club.model.Account;
import com.ascending.training.club.model.Player;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    List<Account> getAll();
    Account save(Account account);
    boolean delete(Long id);
    Account getAccountById(Long id);
}
