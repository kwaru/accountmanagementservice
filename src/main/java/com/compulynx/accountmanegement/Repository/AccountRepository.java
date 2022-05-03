package com.compulynx.accountmanegement.Repository;

import com.compulynx.accountmanegement.Entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Page<Account> findAll(Pageable pageable);
    Account findByAccountName(String accountName);

    @Query("select a from Account a join Customer c on a.id= c.id where c.username=?1")
    Account findAccountId(String customerName);
}
