package com.compulynx.accountmanegement.Repository;

import com.compulynx.accountmanegement.Entity.Role;
import com.compulynx.accountmanegement.Utils.CustomerRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(CustomerRoles name);
}
