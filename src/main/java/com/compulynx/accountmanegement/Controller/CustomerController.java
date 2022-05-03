package com.compulynx.accountmanegement.Controller;


import com.compulynx.accountmanegement.Configuration.JwtUtils;
import com.compulynx.accountmanegement.Entity.Account;
import com.compulynx.accountmanegement.Entity.Customer;
import com.compulynx.accountmanegement.Entity.LoginRequest;
import com.compulynx.accountmanegement.Entity.SignupRequest;
import com.compulynx.accountmanegement.Repository.AccountRepository;
import com.compulynx.accountmanegement.Repository.CustomerRepository;
import com.compulynx.accountmanegement.Repository.RoleRepository;
import com.compulynx.accountmanegement.Service.CustomerInterfaceImpl;
import com.compulynx.accountmanegement.Utils.GenerateRandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

   private CustomerInterfaceImpl customerInterface;


   @Autowired
    public CustomerController(CustomerInterfaceImpl customerInterface) {
        this.customerInterface = customerInterface;
    }

    @PostMapping("/customers/create")
    public ResponseEntity <?> createCustomer(@RequestBody SignupRequest customer){



        return  new ResponseEntity<>(customerInterface.registerCustomer(customer), HttpStatus.CREATED);
    }



    @PostMapping("/customers/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

       return new ResponseEntity<>(customerInterface.authenticateUser(loginRequest),HttpStatus.OK);
    }




}
