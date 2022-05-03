package com.compulynx.accountmanegement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor@AllArgsConstructor@Data
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "account_name",length = 150)
    private String accountName;
    @Column(name ="account_balance",precision = 8, scale =2)
    private Double accountBalance=0.00;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="customerId")
    private Customer customer;



    @Column(name = "date_opened",updatable = false)
    private Date  dateOpened= new Date();
    @Column(name = "date_modified")
    private Date dateModified= new Date();


    public Account(String accountName, Customer customer) {
        this.accountName = accountName;
        this.customer = customer;
    }
}
