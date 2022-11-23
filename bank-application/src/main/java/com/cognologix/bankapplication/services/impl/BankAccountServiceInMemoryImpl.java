package com.cognologix.bankapplication.services.impl;

import com.cognologix.bankapplication.dto.*;
import com.cognologix.bankapplication.exceptions.AccountDeletionException;
import com.cognologix.bankapplication.exceptions.InsufficientInitialBalanceException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ZeroDepositionException;
import com.cognologix.bankapplication.inmemory.BankAccountStorage;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.services.BankAccountServiceInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class BankAccountServiceInMemoryImpl implements BankAccountServiceInMemory {

    @Autowired
    BankAccountStorage bankAccountStorage;

    // service to create a bank account in bank storage
    @Override
    public BankAccountCreationResponse createBankAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        BankAccountCreationResponse bankAccountCreationResponse = new BankAccountCreationResponse();
        try {
            if (bankAccountCreationRequest.getAccountType().isEmpty()) {
                throw new NullArgumentException("Request object is invalid.");
            } else if (bankAccountCreationRequest.getInitialDepositAmount() < 1000) {
                throw new InsufficientInitialBalanceException("Initial balance cannot be less than ₹1000");
            } else if (bankAccountCreationRequest.getInitialDepositAmount().isNaN()) {
                throw new NumberFormatException("Amount must be number.");
            } else {
                BankAccount bankAccount = bankAccountStorage.createBankAccount(bankAccountCreationRequest);
                if (Objects.isNull(bankAccount)) {
                    throw new NullPointerException("Bank account creation response is null.");
                } else {
                    bankAccountCreationResponse.setGeneratedAccountNumber(bankAccount.getAccountNumber());
                    bankAccountCreationResponse.setSuccess(true);
                    if (bankAccountCreationResponse.getSuccess()) {
                        bankAccountCreationResponse.setMessage("Bank account created successfully.");
                    }
                }
            }
        } catch (NullArgumentException | NumberFormatException | InsufficientInitialBalanceException nfe) {
            System.out.println(nfe.getMessage());
        }
        return bankAccountCreationResponse;
    }

    // service to deposit amount
    @Override
    public DepositAmountResponse depositAmount(AmountRequest amountRequest) {
        DepositAmountResponse depositAmountResponse = new DepositAmountResponse();
        try {
            if (amountRequest.getTransactionAmount() == 0) {
                throw new ZeroDepositionException("Deposit amount cannot be 0.");
            } else if (amountRequest.getAccountNumber() == null) {
                throw new NullArgumentException("Account number cannot be null.");
            } else {
                BankAccount bankAccount = bankAccountStorage.depositAmount(amountRequest);
                depositAmountResponse.setAccountNumber(bankAccount.getAccountNumber());
                depositAmountResponse.setDepositDate(LocalDate.now());
                depositAmountResponse.setDepositedAmount(bankAccount.getAccountBalance());
                if (depositAmountResponse.getAccountNumber() != null) {
                    depositAmountResponse.setMessage("Amount deposited successfully.");
                }
            }
        } catch (ZeroDepositionException | NullArgumentException zde) {
            System.out.println(zde.getMessage());
        }
        return depositAmountResponse;
    }

    // service to withdraw amount
    @Override
    public WithdrawAmountResponse withdrawAmount(AmountRequest amountRequest) {
        WithdrawAmountResponse withdrawAmountResponse = new WithdrawAmountResponse();
        try {
            if (amountRequest.getTransactionAmount() <= 0) {
                throw new ZeroDepositionException("Withdrawal amount cannot be 0 or less.");
            } else if ((bankAccountStorage.getAccountBalance(amountRequest.getAccountNumber())) <= amountRequest.getTransactionAmount()) {
                throw new InsufficientInitialBalanceException("Insufficient Account balance. Balance is less than requested amount.");
            } else if (bankAccountStorage.getAccountBalance(amountRequest.getAccountNumber()) > amountRequest.getTransactionAmount()) {
                if ((bankAccountStorage.getAccountBalance(amountRequest.getAccountNumber()) - amountRequest.getTransactionAmount()) < 1000) {
                    throw new InsufficientInitialBalanceException("Insufficient Account balance.");
                } else {
                    Double currentAccountBalance = bankAccountStorage.getAccountBalance(amountRequest.getAccountNumber());
                    BankAccount bankAccount = bankAccountStorage.withdrawAmount(amountRequest);
                    withdrawAmountResponse.setAccountNumber(bankAccount.getAccountNumber());
                    withdrawAmountResponse.setWithdrawalDate(LocalDate.now());
                    withdrawAmountResponse.setAccountBalance(bankAccount.getAccountBalance());
                    if (withdrawAmountResponse.getAccountNumber() != null) {
                        withdrawAmountResponse.setMessage("Amount withdrawal is successful.");
                    }
                }
            }
        } catch (InsufficientInitialBalanceException | ZeroDepositionException zde) {
            System.out.println(zde.getMessage());
        }
        return withdrawAmountResponse;
    }

    // service to show balance in account
    @Override
    public Double showAccountBalanceByAccountNumber(Long accountNumber) {
        Double balance = 0.0;
        try {
            if (accountNumber == null) {
                throw new NullArgumentException("Account number cannot be null.");
            }
            balance = bankAccountStorage.getAccountBalance(accountNumber);
        } catch (NullArgumentException nae) {
            System.out.println(nae.getMessage());
        }
        return balance;
    }

    @Override
    public String deleteBankAccount(Long accountNumber) {
        String message = "";
        try {
            if (accountNumber == null) {
                throw new NullArgumentException("Account number cannot be null.");
            }
            BankAccount bankAccount1 = bankAccountStorage.deleteAccount(accountNumber);
            if (bankAccount1.getAccountStatus().equals("ACTIVE")) {
                throw new AccountDeletionException("Account deletion failed.");
            } else {
                message = "Account deleted successfully";
                return message;
            }
        } catch (AccountDeletionException ade) {
            System.out.println(ade.getMessage());
        }
        return message;
    }
}
