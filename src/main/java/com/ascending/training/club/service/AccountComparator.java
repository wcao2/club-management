package com.ascending.training.club.service;

import com.ascending.training.club.model.Account;

import java.util.Comparator;

//External comparator
public class AccountComparator implements Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        return (a1.getBalance().subtract(a2.getBalance())).intValue();
    }
}
