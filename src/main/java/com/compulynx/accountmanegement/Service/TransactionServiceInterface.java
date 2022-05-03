package com.compulynx.accountmanegement.Service;

import com.compulynx.accountmanegement.Entity.Transactions;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionServiceInterface {

    List<Transactions> getAccountMinistatement();
    List<Transactions> getAllTransactions(Integer pageNumber,Integer pageSize);
    Transactions findTransactionByTransactionId(String transactionId);


}
