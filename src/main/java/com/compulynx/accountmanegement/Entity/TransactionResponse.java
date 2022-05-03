package com.compulynx.accountmanegement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class TransactionResponse {
    private Double accountBalance;
    private String Description;
    private Date transactionDate;

}
