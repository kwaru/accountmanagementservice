package com.compulynx.accountmanegement.Entity;

import com.compulynx.accountmanegement.Utils.ACCOUNTMANAGEMENT;
import com.compulynx.accountmanegement.Utils.GenerateRandomString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Data
@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name ="customer_fname",length = 100)
    private String firstName;
    @Column(name ="customer_lname",length = 100)
    private String lastName;
    private ACCOUNTMANAGEMENT customerInTrash=ACCOUNTMANAGEMENT.NO;
    @NotBlank
    @Size(max = 30)
    @Column(name ="customer_username",length = 30)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    @Column(name ="customer_email",length = 100)
    private String email;
    @NotBlank
    @Size(max = 100)
    @Column(name ="customer_customerid",length = 100)
    private String customerId;

    @NotBlank
    @Size(max = 120)
    @Column(name ="customer_password")
    private String password;
    @Column(name ="customer_registerdate")
    private Date customerRegisteredOn= new Date();
    @Column(name ="customer_editeddate")
    private Date customerModifiedOn = new Date();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Customer() {
    }

    public Customer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(String firstName, String lastName, ACCOUNTMANAGEMENT customerInTrash, String username, String email, String customerId, String password, Date customerRegisteredOn, Date customerModifiedOn, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerInTrash = customerInTrash;
        this.username = username;
        this.email = email;
        this.customerId = customerId;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ACCOUNTMANAGEMENT getCustomerInTrash() {
        return customerInTrash;
    }

    public void setCustomerInTrash(ACCOUNTMANAGEMENT customerInTrash) {
        this.customerInTrash = customerInTrash;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCustomerRegisteredOn() {
        return customerRegisteredOn;
    }

    public void setCustomerRegisteredOn(Date customerRegisteredOn) {
        this.customerRegisteredOn = customerRegisteredOn;
    }

    public Date getCustomerModifiedOn() {
        return customerModifiedOn;
    }

    public void setCustomerModifiedOn(Date customerModifiedOn) {
        this.customerModifiedOn = customerModifiedOn;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
