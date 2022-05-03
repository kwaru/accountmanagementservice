package com.compulynx.accountmanegement.Service;

import com.compulynx.accountmanegement.Entity.Account;
import com.compulynx.accountmanegement.Entity.Customer;
import com.compulynx.accountmanegement.Entity.TransactionResponse;
import com.compulynx.accountmanegement.Entity.Transactions;
import com.compulynx.accountmanegement.ExceptionConfig.ResourceNotFoundException;
import com.compulynx.accountmanegement.Repository.AccountRepository;
import com.compulynx.accountmanegement.Repository.CustomerRepository;
import com.compulynx.accountmanegement.Repository.TransactionRepository;
import com.compulynx.accountmanegement.Utils.ACCOUNTMANAGEMENT;
import com.compulynx.accountmanegement.Utils.GenerateRandomString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountInterfaceImpl implements AccountServiceInterface{

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public AccountInterfaceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
                                TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Create Customer Account.
     *
     * @return
     */

    @Override
    public Account createAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Customer customer = customerRepository.findByUsername(userName).get();
        Account account = new Account(new GenerateRandomString().generateAccountId(customer.getFirstName()),customer);
        return accountRepository.save(account);
    }



    /**
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Account> getAccounts(Integer pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Account> newpage=accountRepository.findAll(pageable);
      return newpage.toList();
    }

    /**
     *
     * @param withDrawlAmount
     * @return
     */
    @Override
    public TransactionResponse withDraw(Double withDrawlAmount) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        // get current user account and update is balance
        Account account = accountRepository.findAccountId(userName);
        if((account.getAccountBalance()>=withDrawlAmount)&&(account.getAccountBalance()>0)) {
            account.setAccountBalance(account.getAccountBalance() - withDrawlAmount);
            account.setDateModified(new Date());
            accountRepository.save(account);
            // record the transaction details
            Transactions depositTransaction = new Transactions(ACCOUNTMANAGEMENT.CREDIT, withDrawlAmount, new GenerateRandomString().generateTransactionId(), new Date(),
                    account);
            transactionRepository.save(depositTransaction);
            TransactionResponse transactionResponse = new TransactionResponse(account.getAccountBalance(),"Account Balance after Money Transfer from ",new Date());
            return transactionResponse;
        }

        return null;
    }

    /**
     *
     * @param depositAmount
     * @return
     */
    @Override
    public TransactionResponse deposit(Double depositAmount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        // get current user account and update is balance
        Account account = accountRepository.findAccountId(userName);
        account.setAccountBalance(account.getAccountBalance()+depositAmount);
        account.setDateModified(new Date());
        //update the account
        accountRepository.save(account);
    // record the transaction details
        Transactions depositTransaction = new Transactions(ACCOUNTMANAGEMENT.CREDIT,depositAmount,new GenerateRandomString().generateTransactionId(),new Date(),
                account);
        transactionRepository.save(depositTransaction);
        TransactionResponse transactionResponse = new TransactionResponse(account.getAccountBalance(),"Money deposit made to your account ",new Date());


        return transactionResponse;
    }

    /**
     *
     * @param transferAmount
     * @param receivingAcctName
     * @return
     */
    @Override
    public TransactionResponse transferFunds(Double transferAmount,String receivingAcctName) {
        //from current user account
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        // get current user account and update is balance
        Account senderAccount = accountRepository.findAccountId(userName);

        // check if sender has enough funds
        if(senderAccount.getAccountBalance()>transferAmount){
            Account receivingAccount = accountRepository.findByAccountName(receivingAcctName);
            if(receivingAccount!=null){
                // update accounts
                senderAccount.setAccountBalance(senderAccount.getAccountBalance()-transferAmount);
                receivingAccount.setAccountBalance(receivingAccount.getAccountBalance()+transferAmount);
                //persist changes to the db for each account
                accountRepository.save(senderAccount);
                accountRepository.save(receivingAccount);

                // record transaction
                Transactions transferTransactionSender = new Transactions(ACCOUNTMANAGEMENT.FUND_TRANSFER_DEBIT, transferAmount, new GenerateRandomString().generateTransactionId(), new Date(),
                        senderAccount);
                transactionRepository.save(transferTransactionSender);

                Transactions transferTransactionReceiver = new Transactions(ACCOUNTMANAGEMENT.FUND_TRANSFER_CREDIT, transferAmount, new GenerateRandomString().generateTransactionId(), new Date(),
                        receivingAccount);
                transactionRepository.save(transferTransactionReceiver);
                // return sender account balance
                TransactionResponse transactionResponse = new TransactionResponse(senderAccount.getAccountBalance(),"Account Balance after Money Transfer from "+senderAccount.getCustomer().getFirstName()+"to "+receivingAccount.getCustomer().getFirstName(),new Date());
                return transactionResponse;
            }

        }


        return null;
    }
}
