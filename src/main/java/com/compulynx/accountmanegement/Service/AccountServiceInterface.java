package com.compulynx.accountmanegement.Service;

import com.compulynx.accountmanegement.Entity.Account;
import com.compulynx.accountmanegement.Entity.TransactionResponse;

import java.util.List;

public interface AccountServiceInterface {

    Account createAccount();
    List<Account> getAccounts(Integer pageNumber, int Pagesize);
    TransactionResponse withDraw( Double withDrawlAmount);
    TransactionResponse deposit(Double depositAmount);
    TransactionResponse transferFunds(Double transferAmount, String receivingAccntName);
}
