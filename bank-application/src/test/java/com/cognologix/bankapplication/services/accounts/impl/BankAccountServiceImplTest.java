package com.cognologix.bankapplication.services.accounts.impl;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.dto.AccountBalanceResponse;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.dto.BankStatementResponse;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.DepositAmountResponse;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.dto.TransferAmountResponse;
import com.cognologix.bankapplication.dto.WithdrawAmountResponse;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.Transaction;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.BankAccountRepository;
import com.cognologix.bankapplication.repositories.TransactionRepository;
import com.cognologix.bankapplication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BankAccountServiceImplTest.class})
class BankAccountServiceImplTest {

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    UserRepository userRepository;

    List<BankAccount> bankAccounts = new ArrayList<>();
    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());
    BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest("Savings", 2000.00, "BR123", 1);
    BankAccount bankAccount = new BankAccount(123456L, "Manjula", String.valueOf(LocalDateTime.now()), "Savings", "INR", "BR123", 2000.00, 1, Constants.ACTIVE.name(), user);

    BankAccount secondAccount = new BankAccount(999999L, "Manjula", String.valueOf(LocalDateTime.now()), "Current", "INR", "BR123", 5000.00, 1, Constants.ACTIVE.name(), user);

    AmountRequest amountRequest = new AmountRequest(123456L, 500.00);

    Double amountAfterDeposition = bankAccount.getAccountBalance() + amountRequest.getTransactionAmount();

    String transactionId = String.valueOf(UUID.randomUUID());
    Transaction depositTransaction = new Transaction(transactionId, amountRequest.getTransactionAmount(), String.valueOf(LocalDateTime.now()), amountRequest.getAccountNumber(), amountAfterDeposition);

    Double amountAfterWithdrawal = bankAccount.getAccountBalance() - amountRequest.getTransactionAmount();

    Transaction withdrawTransaction = new Transaction(transactionId, amountRequest.getTransactionAmount(), String.valueOf(LocalDateTime.now()), amountRequest.getAccountNumber(), amountAfterWithdrawal);

    TransferAmountRequest transferAmountRequest = new TransferAmountRequest(999999L, 123456L, 500.00);

    double balanceAfterWithdraw = secondAccount.getAccountBalance() - transferAmountRequest.getTransactionAmount();

    Double balanceAfterDeposit = bankAccount.getAccountBalance() + transferAmountRequest.getTransactionAmount();

    Transaction transferTransaction = new Transaction(transactionId, transferAmountRequest.getTransactionAmount(), String.valueOf(LocalDateTime.now()), secondAccount.getAccountNumber(), bankAccount.getAccountNumber(), balanceAfterWithdraw, balanceAfterDeposit);

    List<Transaction> transactionsList = new ArrayList<>();

    List<BankStatementResponse> bankStatementResponsesExpected = new ArrayList<>();

    @Test
    @DisplayName(value = "Should create a user bank account.")
    void test_createUsersBankAccount() {
        when(userRepository.findByIdEquals(bankAccountCreationRequest.getCustomerId())).thenReturn(user);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        BankAccountCreationResponse actual = bankAccountService.createUsersBankAccount(bankAccountCreationRequest);
        BankAccountCreationResponse expected = new BankAccountCreationResponse(123456L, Constants.ACCOUNT_CREATED.name(), true);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Should find customer by customer id.")
    void test_findByCustomerId() {
        when(bankAccountRepository.findByCustomerIdEquals(user.getId())).thenReturn(bankAccounts);
        List<BankAccount> actual = bankAccountService.findByCustomerId(1);
        bankAccounts.add(bankAccount);
        List<BankAccount> expected = bankAccounts;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Should deposit requested amount to given bank account by account number.")
    void test_depositAmount() {
        when(bankAccountRepository.findByAccountNumber(123456L)).thenReturn(bankAccount);
        when(transactionRepository.save(depositTransaction)).thenReturn(depositTransaction);

        DepositAmountResponse depositAmountResponseExpected = new DepositAmountResponse(String.valueOf(Constants.AMOUNT_DEPOSITED), true, bankAccount.getAccountNumber(), amountAfterDeposition, String.valueOf(LocalDateTime.now()));
        DepositAmountResponse actual = bankAccountService.depositAmount(amountRequest);
        actual.setDepositDate(depositAmountResponseExpected.getDepositDate());
        assertEquals(depositAmountResponseExpected, actual);
    }

    @Test
    @DisplayName(value = "Should withdraw requested amount from given bank account by account number.")
    void test_withdrawAmount() {
        when(bankAccountRepository.findByAccountNumber(amountRequest.getAccountNumber())).thenReturn(bankAccount);
        when(transactionRepository.save(withdrawTransaction)).thenReturn(withdrawTransaction);

        WithdrawAmountResponse withdrawAmountResponseExpected = new WithdrawAmountResponse(Constants.AMOUNT_WITHDRAWAL.name(), true, bankAccount.getAccountNumber(), String.valueOf(LocalDateTime.now()), amountAfterWithdrawal);
        WithdrawAmountResponse actual = bankAccountService.withdrawAmount(amountRequest);
        actual.setWithdrawalDate(withdrawAmountResponseExpected.getWithdrawalDate());
        assertEquals(withdrawAmountResponseExpected, actual);
    }

    @Test
    @DisplayName(value = "Should transfer requested amount FROM-to-TO bank account.")
    void test_transferAmount() {
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getToAccountNumber())).thenReturn(bankAccount);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getFromAccountNumber())).thenReturn(secondAccount);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        TransferAmountResponse transferAmountResponseExpected = new TransferAmountResponse(Constants.TRANSACTION_SUCCESSFUL.name(), true, 999999L, 123456L, 500.00);
        TransferAmountResponse actual = bankAccountService.transferAmount(transferAmountRequest);
        assertEquals(transferAmountResponseExpected, actual);
    }

    @Test
    @DisplayName(value = "Should display account balance for given account number.")
    void test_showAccountBalanceByAccountNumber() {
        when(bankAccountRepository.findByAccountNumber(secondAccount.getAccountNumber())).thenReturn(secondAccount);
        AccountBalanceResponse expected = new AccountBalanceResponse(Constants.SHOW_BALANCE_SUCCESSFUL.name(), true, secondAccount.getAccountBalance());
        AccountBalanceResponse actual = bankAccountService.showAccountBalanceByAccountNumber(secondAccount.getAccountNumber());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Should set account status to INACTIVE for given account number.")
    void test_deleteBankAccount() {
        when(bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        BaseResponse actual = bankAccountService.deleteBankAccount(bankAccount.getAccountNumber());
        when(bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
        BaseResponse expected = new BaseResponse(Constants.BANK_ACCOUNT_DELETION_SUCCESSFUL.name(), true);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Should return a bank statement for given account number.")
    void test_getBankStatement() {
        transactionsList.add(withdrawTransaction);
        when(transactionRepository.findByFromAccountNumber(bankAccount.getAccountNumber())).thenReturn(transactionsList);
        BankStatementResponse bankStatementResponse = new BankStatementResponse(withdrawTransaction.getTransactionId(), withdrawTransaction.getTransactionDate(), withdrawTransaction.getTransactionAmount(), withdrawTransaction.getBalanceAfterWithdrawal(), withdrawTransaction.getBalanceAfterDeposition());
        bankStatementResponsesExpected.add(bankStatementResponse);
        List<BankStatementResponse> actual = bankAccountService.getBankStatement(amountRequest.getAccountNumber());
        actual.get(0).setTransactionDate(bankStatementResponsesExpected.get(0).getTransactionDate());

        assertEquals(bankStatementResponsesExpected, actual);
    }
}