package com.cognologix.bankapplication.inmemory;

import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.services.UserServiceInMemory;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountStorage {
    List<BankAccount> bankAccounts = new ArrayList<>();
    @Autowired
    private UserServiceInMemory userServiceInMemory;

    // creates a bank account for a customer
    public BankAccount createBankAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        BankAccount bankAccount = new BankAccount();
        User user;
        user = userServiceInMemory.getUserById(bankAccountCreationRequest.getCustomerId());
        Long account_number = 100000000000L;
        Integer bank_id = bankAccounts.size() + 1;
        bankAccount.setBank_id(bank_id);
        bankAccount.setAccountNumber(account_number + bankAccounts.size() + 1);
        bankAccount.setAccountHolderName(user.getCustomerName());
        bankAccount.setAccountType(bankAccountCreationRequest.getAccountType());
        bankAccount.setAccountCreationDate(LocalDate.now());
        bankAccount.setAccountBalance(bankAccountCreationRequest.getInitialDepositAmount());
        bankAccount.setBranchName("Shivaji Nagar");
        bankAccount.setIfscCode(bankAccount.getIfscCode());
        bankAccount.setCustomerId(bankAccountCreationRequest.getCustomerId());
        bankAccounts.add(bankAccount);
        return bankAccount;
    }

    // deposit amount: Input: DTO object AmountRequest; Output: BankAccount with all details
    public BankAccount depositAmount(AmountRequest amountRequest) {
        bankAccounts = bankAccounts.stream()
                .map(bankAccount -> {
                    if (bankAccount.getAccountNumber().equals(amountRequest.getAccountNumber())) {
                        bankAccount.setAccountBalance(bankAccount.getAccountBalance() + amountRequest.getTransactionAmount());
                    }
                    return bankAccount;
                }).collect(Collectors.toList());

        return getAccountById(amountRequest.getAccountNumber());
    }

    // withdraw amount: Input: DTO object AmountRequest; Output: BankAccount with all details
    public BankAccount withdrawAmount(@NotNull AmountRequest amountRequest) {
        bankAccounts = bankAccounts.stream()
                .peek(bankAccount -> {
                    if (bankAccount.getAccountNumber().equals(amountRequest.getAccountNumber())) {
                        bankAccount.setAccountBalance(bankAccount.getAccountBalance() - amountRequest.getTransactionAmount());
                    }
                }).collect(Collectors.toList());
        return getAccountById(amountRequest.getAccountNumber());
    }

    // get account details amount: Input: account number; Output: BankAccount with all details
    public BankAccount getAccountById(Long accountNumber) {
        List<BankAccount> bankAccounts1 = bankAccounts.stream()
                .filter(bankAccount -> bankAccount.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
        return bankAccounts1.get(0);
    }

    // get account details by user id: Input: customer id; Output: List of bank accounts of given customer
    public List<BankAccount> getAccountsByCustomerId(Integer customerId) {
        return bankAccounts.stream()
                .filter(bankAccount -> bankAccount.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    // get balance in account: Input: account number; Output: Double value - account balance
    public Double getAccountBalance(Long accountNumber) {
        return getAccountById(accountNumber).getAccountBalance();
    }

    // delete bank account: Input: account number; Output: Delete Bank Account object
    public BankAccount deleteAccount(Long accountNumber) {
        bankAccounts = bankAccounts.stream()
                .peek(bankAccount1 -> {
                    if (bankAccount1.getAccountNumber().equals(accountNumber)) {
                        bankAccount1.setAccountStatus("INACTIVE");
                    }
                }).collect(Collectors.toList());
        return getAccountById(accountNumber);
    }
}
