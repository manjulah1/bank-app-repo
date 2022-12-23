package com.cognologix.bankapplication.repositories;

import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    List<BankAccount> findByCustomerIdEquals(Integer customerId);

    BankAccount findByAccountNumber(Long accountNumber);

    @Modifying
    @Transactional
    @Query(value = "update bank_account bank_acc set bank_acc.account_balance = :accountBalance where bank_acc.account_number = :accountNumber", nativeQuery = true)
    void updateAccountBalance(@Param(value = "accountNumber") Long accountNumber, @Param(value = "accountBalance") Double accountBalance);
}
