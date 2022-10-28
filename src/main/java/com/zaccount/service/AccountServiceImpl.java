package com.zaccount.service;

import com.zaccount.exceptions.FundsException;
import com.zaccount.model.Account;
import com.zaccount.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements AccountService{
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final MoneyManagementService moneyManagementService;

    public AccountServiceImpl(AccountRepository accountRepository, MoneyManagementService moneyManagementService) {
        this.accountRepository = accountRepository;
        this.moneyManagementService = moneyManagementService;
    }
    public Account retrieveAccount(Integer accountNum) throws EntityNotFoundException{
        return accountRepository.getReferenceById(accountNum);
    }
    public String withdrawal(Account account, String pin, int withdrawalAmount) {
        String withdrawalMsg = null;
        if (pinValid(account, pin)) {
            try {
                account.withdrawal(withdrawalAmount);
                try {
                    withdrawalMsg = moneyManagementService.withdrawFromVault(withdrawalAmount);
                } catch (FundsException e) {
                    account.deposit(withdrawalAmount);
                    withdrawalMsg = e.getMessage();
                }
            } catch (FundsException e) {
                withdrawalMsg = e.getMessage();
            }
        }else{
            withdrawalMsg = "Invalid PIN";
        }
        accountRepository.saveAndFlush(account);
        return withdrawalMsg + "Your balance is " +account.getBalance();
    }
    public boolean pinValid (Account account, String pin){
            return account.getPin().equals(pin);
    }
    public String checkBalance(Account account, String pin){
        if(pinValid(account, pin)) {
            return "Your balance is: "+ account.getBalance();
        }else {
            return "Invalid pin";
        }
    }

}
