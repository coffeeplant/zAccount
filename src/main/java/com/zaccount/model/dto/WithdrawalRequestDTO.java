package com.zaccount.model.dto;

import lombok.Data;

@Data
public class WithdrawalRequestDTO {

    private Integer accountNum;
    private String pin;
    private Integer withdrawalAmount;
}
