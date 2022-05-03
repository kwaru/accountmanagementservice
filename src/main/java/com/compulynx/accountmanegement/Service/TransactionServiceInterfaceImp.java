package com.compulynx.accountmanegement.Service;


import com.compulynx.accountmanegement.Entity.Account;
import com.compulynx.accountmanegement.Entity.Transactions;
import com.compulynx.accountmanegement.Repository.AccountRepository;
import com.compulynx.accountmanegement.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceInterfaceImp implements TransactionServiceInterface{

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
@Autowired
    public TransactionServiceInterfaceImp(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Returns ministatement which is
     * The latest 10 transactions done
     * @return
     */
    @Override
    public List<Transactions> getAccountMinistatement() {
        //from current user account
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        // get current user account and update is balance
        Account senderAccount = accountRepository.findAccountId(userName);

        Pageable pageable = PageRequest.of(0,10);
//        Page<Transactions> ministatementPage=transactionRepository.findAll(pageable);
        List<Transactions> transactionsList= transactionRepository.findLastTenTransactions(pageable,senderAccount.getAccountName());
        return transactionsList;
    }

    /**
     * Get all transactions in chunks of specified size
     * and page number which starts from page 0 onwards
     * @param pageNumber
     * @param pageSize
     * @return
     */

    @Override
    public List<Transactions> getAllTransactions(Integer pageNumber,Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Transactions> newpage=transactionRepository.findAll(pageable);
        return newpage.toList();
    }

    /**
     * Finds transaction reference number which uniquely
     * Identify each transaction.
     * @param transactionId
     * @return
     */
    @Override
    public Transactions findTransactionByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionReference(transactionId);
    }
}
