package com.zaccount.service;

import com.zaccount.model.Account;

public interface AccountService {
    Account retrieveAccount(Integer accountNum);
    String withdrawal(Account account, String pin, int withdrawalAmount);
    boolean pinValid (Account account, String pin);
    String checkBalance(Account account, String pin);
}
