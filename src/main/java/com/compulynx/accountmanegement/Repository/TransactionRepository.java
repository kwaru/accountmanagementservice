package com.compulynx.accountmanegement.Repository;

import com.compulynx.accountmanegement.Entity.Transactions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Long> {

    Transactions findByTransactionReference(String transactionId);
// @Query("SELECT r FROM TitleBasics t join TitleRatings r on r.tconst = t.tconst WHERE t.genres=?1 ORDER BY r.averageRating DESC ")
    @Query( "SELECT t FROM Transactions t join Account a on a.accountName=t.account.accountName where a.accountName=?1")
    List<Transactions> findLastTenTransactions(Pageable pageable, String accountName);
}
