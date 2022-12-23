package com.cognologix.bankapplication.controllers.accounts;

import com.cognologix.bankapplication.dto.AccountBalanceResponse;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.dto.BankStatementResponse;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.DepositAmountResponse;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.dto.TransferAmountResponse;
import com.cognologix.bankapplication.dto.WithdrawAmountResponse;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping(path = "/createBankAccount")
    public ResponseEntity<BankAccountCreationResponse> createUsersBankAccount(@RequestBody BankAccountCreationRequest bankAccountCreationRequest) {
        BankAccountCreationResponse bankAccountCreationResponse = bankAccountService.createUsersBankAccount(bankAccountCreationRequest);
        HttpStatus status = Boolean.TRUE.equals(bankAccountCreationResponse.getIsSuccess()) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(bankAccountCreationResponse, status);
    }

    @PutMapping(path = "/deposit")
    public ResponseEntity<DepositAmountResponse> depositAmount(@RequestBody AmountRequest amountRequest) {
        DepositAmountResponse depositAmountResponse = bankAccountService.depositAmount(amountRequest);
        HttpStatus status = Boolean.TRUE.equals(depositAmountResponse.getIsSuccess()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(depositAmountResponse, status);
    }

    @PutMapping(path = "/withdraw")
    public ResponseEntity<WithdrawAmountResponse> withdrawAmount(@RequestBody AmountRequest amountRequest) {
        WithdrawAmountResponse withdrawAmountResponse = bankAccountService.withdrawAmount(amountRequest);
        HttpStatus status = Boolean.TRUE.equals(withdrawAmountResponse.getIsSuccess()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(withdrawAmountResponse, status);
    }

    @PutMapping(path = "/transferAmount")
    public ResponseEntity<TransferAmountResponse> transferAmount(@RequestBody TransferAmountRequest transferAmountRequest) {
        TransferAmountResponse transferAmountResponse = bankAccountService.transferAmount(transferAmountRequest);
        HttpStatus status = Boolean.TRUE.equals(transferAmountResponse.getIsSuccess()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(transferAmountResponse, status);
    }

    @GetMapping(path = "/showBalance")
    public ResponseEntity<AccountBalanceResponse> showAccountBalance(@NotNull @RequestParam("accountNumber") Long accountNumber) {
        AccountBalanceResponse accountBalanceResponse = bankAccountService.showAccountBalanceByAccountNumber(accountNumber);
        HttpStatus status = Boolean.TRUE.equals(accountBalanceResponse.getIsSuccess()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(accountBalanceResponse, status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteBankAccount(@NotNull @RequestParam("accountNumber") Long accountNumber) {
        BaseResponse accountDeletionStatus = bankAccountService.deleteBankAccount(accountNumber);
        HttpStatus status = Boolean.TRUE.equals(accountDeletionStatus.getIsSuccess()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(accountDeletionStatus, status);
    }

    @GetMapping(path = "/getStatement")
    public ResponseEntity<List<BankStatementResponse>> getStatement(@NotNull @RequestParam("accountNumber") Long accountNumber) {
        List<BankStatementResponse> bankStatementResponses = bankAccountService.getBankStatement(accountNumber);
        HttpStatus status = !bankStatementResponses.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(bankStatementResponses, status);
    }
}
