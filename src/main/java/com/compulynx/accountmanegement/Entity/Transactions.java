package com.compulynx.accountmanegement.Entity;


import com.compulynx.accountmanegement.Utils.ACCOUNTMANAGEMENT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@AllArgsConstructor@NoArgsConstructor@Data
@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "transaction_type",nullable = false)
    private ACCOUNTMANAGEMENT transactionType;
    @Column(name ="transaction_amount",nullable = false,precision = 8,scale=2)
    private Double transactionAmount;
    @Column(name ="transaction_reference")
    private String transactionReference;
    @Column(name = "transaction_date",unique = true)
    private Date transactionDate= new Date();
    @ManyToOne
    private Account account;

    public Transactions(ACCOUNTMANAGEMENT transactionType, Double transactionAmount, String transactionReference, Date transactionDate, Account account) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionReference = transactionReference;
        this.transactionDate = transactionDate;
        this.account = account;
    }
}
