package com.zaccount.service;

import com.zaccount.exceptions.FundsException;
import com.zaccount.model.Atm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MoneyManagementServiceImpl implements MoneyManagementService{
    private static Logger logger = LoggerFactory.getLogger(MoneyManagementServiceImpl.class);
    private Atm atm;

    public MoneyManagementServiceImpl(Atm atm){
        this.atm= atm;
    }
    synchronized public String withdrawFromVault(int amount) throws FundsException {
        int availableFunds = atm.getAvailableFunds();
        logger.info("Available ATM funds: " +availableFunds);
        if(amount>availableFunds){
            throw new FundsException("We're sorry. This ATM does not have sufficient cash for this transaction, please try again with a lesser withdrawal amount. ");
        }else{
            String notesAmount = withDrawMinNotes(amount);
            if(!Objects.equals(notesAmount, "")){
                atm.setAvailableFunds(availableFunds-amount);
            }else{
                throw new FundsException("The ATM does not have the notes available for this request. ");
            }
            logger.info("The ATM has this much funds left: "+atm.getAvailableFunds());
            return "You have withdrawn this amount: " +amount+ ". "+notesAmount;
        }
    }
    synchronized public String withDrawMinNotes(int amount) throws FundsException {
        LinkedHashMap<Integer, Integer> theNotes = atm.getNotes();
        StringBuilder notesString = new StringBuilder();

        for(Integer denomination : theNotes.keySet()){
            int currentNumOfNotes = 0;
            while(amount>0 && amount>= denomination && theNotes.get(denomination)>0){
                amount = amount-denomination;
                theNotes.put(denomination, theNotes.get(denomination)-1);
                currentNumOfNotes ++;
            }
            if(currentNumOfNotes>0){
                notesString.append(currentNumOfNotes).append(" \u20ac").append(denomination).append("s; ");
            }
        }
        if(notesString.toString().equals("")){
            throw new FundsException("The notes to dispense this amount are not available");
        }else {
            return "The amount will be dispensed in " + notesString;
        }
    }

}
