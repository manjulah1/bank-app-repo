package com.cognologix.bankapplication.controllers.inmemorycontrollers;

import com.cognologix.bankapplication.dto.*;
import com.cognologix.bankapplication.services.BankAccountServiceInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/accounts")
public class BankAccountControllerInMemory {

    @Autowired
    private BankAccountServiceInMemory bankAccountServiceInMemory;

    // controller to create bank account
    // Input: dto object of BankAccountCreationRequest(customerId, initialBalance, accountType)
    // Output: ResponseEntity object of BankAccountCreationResponse(accountNumber, message)
    @PostMapping("/createAccount")
    public ResponseEntity<BankAccountCreationResponse> createBankAccount(@Valid @RequestBody BankAccountCreationRequest bankAccountCreationRequest) {
        BankAccountCreationResponse bankAccountCreationResponse = new BankAccountCreationResponse();
        bankAccountCreationResponse = bankAccountServiceInMemory.createBankAccount(bankAccountCreationRequest);
        final HttpStatus status = Objects.isNull(bankAccountCreationResponse) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(bankAccountCreationResponse, status);
    }

    // controller to deposit amount
    // input: DTO object AmountRequest
    // output: DTO object DepositAmountResponse
    @PutMapping("/deposit")
    public ResponseEntity<DepositAmountResponse> depositAmount(@RequestBody AmountRequest amountRequest) {
        DepositAmountResponse depositAmountResponse = bankAccountServiceInMemory.depositAmount(amountRequest);
        HttpStatus status = Objects.isNull(depositAmountResponse) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(depositAmountResponse, status);

    }

    // controller to withdraw amount
    // input: DTO object AmountRequest
    // output: DTO object WithdrawAmountResponse
    @PutMapping("/withdraw")
    public ResponseEntity<WithdrawAmountResponse> withdrawAmount(@RequestBody AmountRequest amountRequest) {
        WithdrawAmountResponse withdrawAmountResponse = bankAccountServiceInMemory.withdrawAmount(amountRequest);
        HttpStatus status = Objects.isNull(withdrawAmountResponse) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(withdrawAmountResponse, status);
    }

    // controller to show account balance
    // input: Account number
    // output: Double value : Balance in account
    @GetMapping("/showBalance")
    public ResponseEntity<Double> showAccountBalanceByAccountNumber(@RequestParam("acc_number") Long accountNumber) {
        Double balance = bankAccountServiceInMemory.showAccountBalanceByAccountNumber(accountNumber);
        HttpStatus status = balance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(balance, status);
    }

    @DeleteMapping("/delete/{account_number}")
    public ResponseEntity<String> deleteBankAccount(@PathVariable("account_number") Long accountNumber) {
        String message = bankAccountServiceInMemory.deleteBankAccount(accountNumber);
        HttpStatus status = message.isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(message, status);
    }
}
