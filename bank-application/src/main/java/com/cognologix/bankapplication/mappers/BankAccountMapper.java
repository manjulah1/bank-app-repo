package com.cognologix.bankapplication.mappers;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.Transaction;
import com.cognologix.bankapplication.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BankAccountMapper {
    public BankAccount maptoBankAccountEntity(BankAccountCreationRequest bankAccountCreationRequest, User user) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountCreationDate(String.valueOf(LocalDateTime.now()));
        bankAccount.setAccountHolderName(user.getCustomerName());
        bankAccount.setAccountType(bankAccountCreationRequest.getAccountType());
        bankAccount.setBranchCode(bankAccountCreationRequest.getBranchName());
        bankAccount.setAccountBalance(bankAccountCreationRequest.getInitialDepositAmount());
        bankAccount.setCustomerId(bankAccountCreationRequest.getCustomerId());
        bankAccount.setUser(user);

        return bankAccount;
    }

    public Transaction mapToTransactionEntity(AmountRequest amountRequest, Double amount, String transactionType) {
        Transaction transaction = new Transaction();
        String transactionId = String.valueOf(UUID.randomUUID());
        transaction.setTransactionId(transactionId);
        transaction.setTransactionAmount(amountRequest.getTransactionAmount());
        transaction.setTransactionDate(String.valueOf(LocalDateTime.now()));
        if (transactionType.equals(Constants.DEPOSIT.name())) {
            transaction.setBalanceAfterDeposition(amount);
            transaction.setToAccountNumber(amountRequest.getAccountNumber());
        } else if (transactionType.equals(Constants.WITHDRAW.name())) {
            transaction.setBalanceAfterWithdrawal(amount);
            transaction.setFromAccountNumber(amountRequest.getAccountNumber());
        }

        return transaction;
    }
}
