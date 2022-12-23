package com.cognologix.bankapplication.repositories;

import com.cognologix.bankapplication.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByFromAccountNumber(Long accountNumber);

    List<Transaction> findByToAccountNumber(Long accountNumber);
}
