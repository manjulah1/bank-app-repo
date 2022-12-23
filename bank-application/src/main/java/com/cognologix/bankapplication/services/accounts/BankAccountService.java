package com.cognologix.bankapplication.services.accounts;

import com.cognologix.bankapplication.dto.AccountBalanceResponse;
import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankStatementResponse;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.DepositAmountResponse;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.dto.TransferAmountResponse;
import com.cognologix.bankapplication.dto.WithdrawAmountResponse;
import com.cognologix.bankapplication.models.BankAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankAccountService {
    BankAccountCreationResponse createUsersBankAccount(BankAccountCreationRequest bankAccountCreationRequest);

    List<BankAccount> findByCustomerId(Integer customerId);

    DepositAmountResponse depositAmount(AmountRequest amountRequest);

    WithdrawAmountResponse withdrawAmount(AmountRequest amountRequest);

    TransferAmountResponse transferAmount(TransferAmountRequest transferAmountRequest);

    AccountBalanceResponse showAccountBalanceByAccountNumber(Long accountNumber);

    BaseResponse deleteBankAccount(Long accountNumber);

    List<BankStatementResponse> getBankStatement(Long accountNumber);

    List<BankAccount> getAllBankAccounts();

}
