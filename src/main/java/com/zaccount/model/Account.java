package com.zaccount.model;

import com.zaccount.exceptions.FundsException;
import lombok.*;

import javax.persistence.*;


@Table(name = "account")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @EqualsAndHashCode.Include
    private Integer account_num;
    @EqualsAndHashCode.Include
    private String pin;
    private Integer balance;
    private Integer overdraft;
    public Account(int accountNum){
        this.account_num=accountNum;
    }

    public synchronized int withdrawal(int withdrawalAmount) throws FundsException {
        int availableFunds = this.balance + this.overdraft;
        if(availableFunds < withdrawalAmount){
            throw new FundsException("Your account does not have available funds");
        }else {
            this.balance = balance - withdrawalAmount;
            return this.balance;
        }
    }
    public synchronized void deposit(int depAmount){
        this.balance = balance+depAmount;
    }

    public synchronized double getBalance() {
        return this.balance;
    }
}
