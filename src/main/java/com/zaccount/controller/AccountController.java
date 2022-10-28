package com.zaccount.controller;

import com.zaccount.model.Account;
import com.zaccount.model.dto.WithdrawalRequestDTO;
import com.zaccount.service.AccountService;
import com.zaccount.service.MoneyManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;
    private final MoneyManagementService moneyManagementService;

    public AccountController(AccountService accountService, MoneyManagementService moneyManagementService) {
        this.accountService = accountService;
        this.moneyManagementService = moneyManagementService;
    }

    @PostMapping("/withdrawal")
    public String withdrawalRequest(@RequestBody WithdrawalRequestDTO withdrawalRequestDTO){
        Account activeAccount = accountService.retrieveAccount(withdrawalRequestDTO.getAccountNum());
        try{
            return accountService.withdrawal(activeAccount,withdrawalRequestDTO.getPin(),withdrawalRequestDTO.getWithdrawalAmount());
        }catch(EntityNotFoundException e){
            logger.info(e.getMessage());
            return "Account not found, please enter a valid account number";
        }
    }

    @GetMapping(value = "/balance")
    public String getBalance(@RequestParam int accountNum, String pin) {
        Account activeAccount = accountService.retrieveAccount(accountNum);
        try{
            return accountService.checkBalance(activeAccount, pin);
        }catch(EntityNotFoundException e){
            logger.info(e.getMessage());
            return "Account not found, please enter a valid account number";
        }
    }

}
