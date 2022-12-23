package com.cognologix.bankapplication.exceptions.handlers;

import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import org.springframework.stereotype.Component;

@Component
public class BankAccountExceptionHandler {
    public Boolean checkNullArgumentForCreateBankAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        return bankAccountCreationRequest.getAccountType() == null || bankAccountCreationRequest.getInitialDepositAmount() == null || bankAccountCreationRequest.getBranchName() == null || bankAccountCreationRequest.getCustomerId() == null;
    }

    public Boolean checkNullArgumentForAmountRequest(AmountRequest amountRequest) {
        return amountRequest.getTransactionAmount() == null || amountRequest.getAccountNumber() == null;
    }

    public Boolean checkNullArgumentForTransferAmountRequest(TransferAmountRequest transferAmountRequest) {
        return transferAmountRequest.getTransactionAmount() == null || transferAmountRequest.getFromAccountNumber() == null || transferAmountRequest.getToAccountNumber() == null;
    }
}
