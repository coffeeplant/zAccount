package com.zaccount.service;

import com.zaccount.exceptions.FundsException;
public interface MoneyManagementService {

    String withdrawFromVault(int amount) throws FundsException;
    String withDrawMinNotes(int amount) throws FundsException;

}
