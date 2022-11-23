package com.cognologix.bankapplication.services;

import com.cognologix.bankapplication.dto.*;
import com.cognologix.bankapplication.models.BankAccount;
import org.springframework.stereotype.Service;

@Service
public interface BankAccountServiceInMemory {
    BankAccountCreationResponse createBankAccount(BankAccountCreationRequest bankAccountCreationRequest);

    DepositAmountResponse depositAmount(AmountRequest amountRequest);

    WithdrawAmountResponse withdrawAmount(AmountRequest amountRequest);

    Double showAccountBalanceByAccountNumber(Long accountNumber);

    String deleteBankAccount(Long accountNumber);
}
