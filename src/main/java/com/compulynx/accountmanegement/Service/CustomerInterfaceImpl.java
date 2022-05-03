package com.compulynx.accountmanegement.Service;

import com.compulynx.accountmanegement.Configuration.JwtUtils;
import com.compulynx.accountmanegement.Entity.*;
import com.compulynx.accountmanegement.Repository.AccountRepository;
import com.compulynx.accountmanegement.Repository.CustomerRepository;
import com.compulynx.accountmanegement.Repository.RoleRepository;
import com.compulynx.accountmanegement.Utils.ACCOUNTMANAGEMENT;
import com.compulynx.accountmanegement.Utils.CustomerRoles;
import com.compulynx.accountmanegement.Utils.GenerateRandomString;
import com.compulynx.accountmanegement.Utils.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerInterfaceImpl implements  UserDetailsService{
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
   private  PasswordEncoder encoder;
    private JwtUtils jwtUtils;

    @Autowired
    public CustomerInterfaceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(customerId).
                orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + customerId));
        return CustomerDetailsImpl.build(customer);
    }
//
public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    CustomerDetailsImpl userDetails = (CustomerDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
}




    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SignupRequest signUpRequest) {
        if (customerRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new Customer account auto fields
        String customerPin = new GenerateRandomString().userPin();
        String customerId = new GenerateRandomString().generateTransactionId();
        Set<Role> roles = new HashSet<>();
        roles.add( roleRepository.findByName(CustomerRoles.ROLE_USER));


        Customer returnedCustomer = new Customer(signUpRequest.getFirstName(),
                signUpRequest.getLastName(),ACCOUNTMANAGEMENT.NO,signUpRequest.getUsername(), signUpRequest.getEmail(),customerId, customerPin,new Date(),new Date(),roles);
        Customer customer = new Customer(signUpRequest.getFirstName(),
                signUpRequest.getLastName(),ACCOUNTMANAGEMENT.NO,signUpRequest.getUsername(), signUpRequest.getEmail(),customerId, encoder.encode(customerPin),new Date(),new Date(),roles);


        returnedCustomer.setId(customerRepository.save(customer).getId());;
        CustomerResponseObject customerResponseObject = new CustomerResponseObject(returnedCustomer.getId(),returnedCustomer.getFirstName(),
                returnedCustomer.getLastName(),returnedCustomer.getCustomerInTrash(), returnedCustomer.getUsername(),
                returnedCustomer.getEmail(),returnedCustomer.getCustomerId(), returnedCustomer.getPassword(),
                returnedCustomer.getCustomerRegisteredOn(),returnedCustomer.getCustomerModifiedOn());

        return new ResponseEntity<>(customerResponseObject, HttpStatus.CREATED);
    }




}
