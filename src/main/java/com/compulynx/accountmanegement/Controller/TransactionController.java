package com.compulynx.accountmanegement.Controller;


import com.compulynx.accountmanegement.Entity.Account;
import com.compulynx.accountmanegement.Entity.Transactions;
import com.compulynx.accountmanegement.ExceptionConfig.ResourceNotFoundException;
import com.compulynx.accountmanegement.Service.TransactionServiceInterfaceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private TransactionServiceInterfaceImp transactionServiceInterfaceImp;

    public TransactionController(TransactionServiceInterfaceImp transactionServiceInterfaceImp) {
        this.transactionServiceInterfaceImp = transactionServiceInterfaceImp;
    }
    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions(@RequestParam(name = "pagesize")Integer pageNumber,
                                                             @RequestParam(name = "pageSize") Integer pageSize){
        List<Transactions> transactionsList=transactionServiceInterfaceImp.getAllTransactions(pageNumber,pageSize);
        if(transactionsList.isEmpty())
            return  new ResponseEntity<>("You have not transacted",HttpStatus.OK);
        return new ResponseEntity<>(transactionsList, HttpStatus.OK);
    }

    @GetMapping("/transactions/ministatement")
    public ResponseEntity<?> getMinistatement(){
        List<Transactions> ministatement =transactionServiceInterfaceImp.getAccountMinistatement();
        if(ministatement.isEmpty())
            return new ResponseEntity<>("You have not transacted",HttpStatus.OK);
        return new ResponseEntity<>(ministatement,HttpStatus.OK);
    }


    @GetMapping("/transactions/referencenumber")
    public ResponseEntity<?> findTransactionByTransactionId(@RequestParam(name = "transactionReferenceNumber")String transactionReferenceNumber){
        Transactions transaction= transactionServiceInterfaceImp.findTransactionByTransactionId(transactionReferenceNumber);
        if(transaction==null)
            return  new ResponseEntity<>("No transaction found with the reference number "+transactionReferenceNumber,HttpStatus.OK);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }



}
