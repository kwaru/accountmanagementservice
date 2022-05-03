package com.compulynx.accountmanegement.Controller;


import com.compulynx.accountmanegement.Entity.Account;
import com.compulynx.accountmanegement.Entity.TransactionResponse;
import com.compulynx.accountmanegement.Repository.AccountRepository;
import com.compulynx.accountmanegement.Repository.CustomerRepository;
import com.compulynx.accountmanegement.Service.AccountInterfaceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

private AccountInterfaceImpl accountInterface;
    private static final Logger log = LoggerFactory.getLogger(AccountController.class.getName());
    public AccountController(AccountInterfaceImpl accountInterface) {
        this.accountInterface = accountInterface;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(){
        return new ResponseEntity<>(accountInterface.createAccount(),HttpStatus.CREATED);
    }


    @PutMapping("/accounts/deposit")
    public ResponseEntity<?> deposit(@RequestParam(name = "depositAmount")Double depositAmount){
        TransactionResponse balance =accountInterface.deposit(depositAmount);
        if(balance.getAccountBalance()==null)
            return  new ResponseEntity<>("You have insufficient balance", HttpStatus.OK);
        return new  ResponseEntity<>(balance,HttpStatus.OK);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(@RequestParam(name = "pageNumber")Integer pageNumber,
                                                        @RequestParam(name = "pageSize") Integer pageSize){
        List<Account> accounts = accountInterface.getAccounts(pageNumber,pageSize);
        if(accounts.isEmpty())
            return  new ResponseEntity<>("No accounts found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(accounts,HttpStatus.OK);
    }

    @PutMapping("/accounts/withdraw")
    public ResponseEntity<?> withDraw(@RequestParam(name = "amountToWithDraw")Double amountToWithDraw){
        TransactionResponse balance =accountInterface.withDraw(amountToWithDraw);
        if( balance.getAccountBalance()==null)
            return new ResponseEntity<>("Insufficient Balance to complete the requested transaction",HttpStatus.OK);
        return new ResponseEntity<>(balance,HttpStatus.OK);
    }

    @PutMapping("/accounts/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam(name = "amountToTransfer")Double amountToTransfer,
                                                @RequestParam(name = "recipientAccountName") String recipientAccountName){
        TransactionResponse balance =accountInterface.transferFunds(amountToTransfer,recipientAccountName);
        if(balance.getAccountBalance()==null)
            return  new ResponseEntity<>("Insufficient Balance to complete the requested transaction",HttpStatus.OK);

        return new ResponseEntity<>(balance, HttpStatus.OK);
    }


}
