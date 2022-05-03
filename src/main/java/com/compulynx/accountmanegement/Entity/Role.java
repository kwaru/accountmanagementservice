package com.compulynx.accountmanegement.Entity;

import com.compulynx.accountmanegement.Utils.CustomerRoles;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CustomerRoles name;

    public Role() {

    }

    public Role(CustomerRoles name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerRoles getName() {
        return name;
    }

    public void setName(CustomerRoles name) {
        this.name = name;
    }
}
