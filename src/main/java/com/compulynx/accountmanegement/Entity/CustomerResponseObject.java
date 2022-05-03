package com.compulynx.accountmanegement.Entity;

import com.compulynx.accountmanegement.Utils.ACCOUNTMANAGEMENT;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
@Data
@AllArgsConstructor
public class CustomerResponseObject {

    private Long id;
    private String firstName;
    private String lastName;
    private ACCOUNTMANAGEMENT customerInTrash;
    private String username;
    private String email;
    private String customerId;
    private String pin ;
    private Date customerRegisteredOn;
    private Date customerModifiedOn;

}
