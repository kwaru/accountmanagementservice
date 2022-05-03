package com.compulynx.accountmanegement.Repository;

import com.compulynx.accountmanegement.Entity.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
    Boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
