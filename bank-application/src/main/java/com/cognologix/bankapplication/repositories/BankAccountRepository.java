package com.cognologix.bankapplication.repositories;

import com.cognologix.bankapplication.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}
