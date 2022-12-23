package com.cognologix.bankapplication.services.accounts.impl;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.exceptions.AccountDeletionException;
import com.cognologix.bankapplication.exceptions.InsufficientInitialBalanceException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ResourceNotFoundException;
import com.cognologix.bankapplication.exceptions.ZeroAmountException;
import com.cognologix.bankapplication.exceptions.handlers.BankAccountExceptionHandler;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.BankAccountRepository;
import com.cognologix.bankapplication.repositories.TransactionRepository;
import com.cognologix.bankapplication.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BankAccountServiceImplNegativeTest.class})
class BankAccountServiceImplNegativeTest {

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BankAccountExceptionHandler bankAccountExceptionHandler;
    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());

    BankAccount bankAccount = new BankAccount(123456L, "Manjula", String.valueOf(LocalDateTime.now()), "Savings", "INR", "BR123", 2000.00, 1, Constants.ACTIVE.name(), user);

    BankAccount secondAccount = new BankAccount(999999L, "Manjula", String.valueOf(LocalDateTime.now()), "Current", "INR", "BR123", 5000.00, 1, Constants.ACTIVE.name(), user);

    @Test
    @DisplayName(value = "Should throw null argument exception when given null data in request.")
    void should_throw_NullArgumentException_createUsersBankAccount() {
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(null, null, null, null);
        NullArgumentException nullArgumentException = assertThrows(NullArgumentException.class, () -> bankAccountService.createUsersBankAccount(bankAccountCreationRequest));
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), nullArgumentException.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw insufficient balance exception when amount is less than 1000.")
    void should_throw_InsufficientInitialBalanceException_createUsersBankAccount() {
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest("Savings", 500.00, "BR123", 1);
        InsufficientInitialBalanceException exception = assertThrows(InsufficientInitialBalanceException.class, () -> bankAccountService.createUsersBankAccount(bankAccountCreationRequest));
        assertEquals(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw resource not found exception if user/customer not found.")
    void should_throw_ResourceNotFoundException_createUsersBankAccount() {
        when(userRepository.findByIdEquals(1)).thenReturn(user);
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest("Savings", 5000.00, "BR123", 2);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> bankAccountService.createUsersBankAccount(bankAccountCreationRequest));
        assertTrue(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError().contains(exception.getMessage()));
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when customer id is null.")
    void should_throw_NullArgumentException_findByCustomerId() {
        NullArgumentException nullArgumentException = assertThrows(NullArgumentException.class, () -> bankAccountService.findByCustomerId(null));
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), nullArgumentException.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw zero amount exception when deposit amount in request is 0.")
    void should_throw_ZeroAmountException_depositAmount() {
        AmountRequest amountRequest = new AmountRequest(123456L, 0.00);
        ZeroAmountException exception = assertThrows(ZeroAmountException.class, () -> bankAccountService.depositAmount(amountRequest));
        assertEquals(ExceptionMessageConstants.ZERO_AMOUNT.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when account number or transaction amount is null.")
    void should_throw_NullArgumentException_depositAmount() {
        AmountRequest amountRequest = new AmountRequest(null, 500.00);
        NullArgumentException exception = assertThrows(NullArgumentException.class, () -> bankAccountService.depositAmount(amountRequest));
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw resource not found exception if bank account is inactive.")
    void should_throw_ResourceNotFoundException_depositAmount() {
        BankAccount bankAccount = new BankAccount(123456L, "Manjula", String.valueOf(LocalDateTime.now()), "Savings", "INR", "BR123", 2000.00, 1, Constants.INACTIVE.name(), user);
        AmountRequest amountRequest = new AmountRequest(123456L, 500.00);
        when(bankAccountRepository.findByAccountNumber(123456L)).thenReturn(bankAccount);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> bankAccountService.depositAmount(amountRequest));
        assertTrue(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError().contains(exception.getMessage()));
    }

    @Test
    @DisplayName(value = "Should throw zero amount exception when withdraw amount in request is 0.")
    void should_throw_ZeroAmountException_withdrawAmount() {
        when(bankAccountRepository.findByAccountNumber(123456L)).thenReturn(bankAccount);
        AmountRequest amountRequest = new AmountRequest(123456L, 0.00);
        ZeroAmountException exception = assertThrows(ZeroAmountException.class, () -> bankAccountService.withdrawAmount(amountRequest));
        assertEquals(ExceptionMessageConstants.ZERO_AMOUNT.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when request body has null values.")
    void should_throw_NullArgumentException_withdrawAmount() {
        when(bankAccountRepository.findByAccountNumber(123456L)).thenReturn(bankAccount);
        AmountRequest amountRequest = new AmountRequest(null, 500.00);
        NullArgumentException exception = assertThrows(NullArgumentException.class, () -> bankAccountService.withdrawAmount(amountRequest));
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw insufficient balance exception when amount is less than 0 after transaction.")
    void should_throw_InsufficientInitialBalanceException_withdrawAmount() {
        when(bankAccountRepository.findByAccountNumber(123456L)).thenReturn(bankAccount);
        AmountRequest amountRequest = new AmountRequest(123456L, 5000.00);
        InsufficientInitialBalanceException exception = assertThrows(InsufficientInitialBalanceException.class, () -> bankAccountService.withdrawAmount(amountRequest));
        assertEquals(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw zero amount exception when transfer amount is less than 0.")
    void should_throw_ZeroAmountException_transferAmount() {
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(123456L, 999999L, 0.00);
        ZeroAmountException exception = assertThrows(ZeroAmountException.class, () -> bankAccountService.transferAmount(transferAmountRequest));
        assertEquals(ExceptionMessageConstants.ZERO_AMOUNT.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when request body has null values.")
    void should_throw_NullArgumentException_transferAmount() {
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(null, null, 500.00);
        NullArgumentException exception = assertThrows(NullArgumentException.class, () -> bankAccountService.transferAmount(transferAmountRequest));
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw account deletion exception when bank account status is inactive to perform transaction.")
    void should_throw_AccountDeletionException_fromAccountNumber_transferAmount() {
        BankAccount bankAccount = new BankAccount(123456L, "Manjula", String.valueOf(LocalDateTime.now()), "Savings", "INR", "BR123", 2000.00, 1, Constants.INACTIVE.name(), user);

        BankAccount secondAccount = new BankAccount(999999L, "Manjula", String.valueOf(LocalDateTime.now()), "Current", "INR", "BR123", 5000.00, 1, Constants.ACTIVE.name(), user);

        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(123456L, 999999L, 500.00);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getToAccountNumber())).thenReturn(secondAccount);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getFromAccountNumber())).thenReturn(bankAccount);
        AccountDeletionException exception1 = assertThrows(AccountDeletionException.class, () -> bankAccountService.transferAmount(transferAmountRequest));
        assertEquals(ExceptionMessageConstants.ACCOUNT_DELETION.getError(), exception1.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw account deletion exception when bank account status is inactive to perform transaction.")
    void should_throw_AccountDeletionException_toAccountNumber_transferAmount() {
        BankAccount bankAccount = new BankAccount(123456L, "Manjula", String.valueOf(LocalDateTime.now()), "Savings", "INR", "BR123", 2000.00, 1, Constants.ACTIVE.name(), user);

        BankAccount secondAccount = new BankAccount(999999L, "Manjula", String.valueOf(LocalDateTime.now()), "Current", "INR", "BR123", 5000.00, 1, Constants.INACTIVE.name(), user);

        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(123456L, 999999L, 500.00);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getToAccountNumber())).thenReturn(secondAccount);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getFromAccountNumber())).thenReturn(bankAccount);
        AccountDeletionException exception = assertThrows(AccountDeletionException.class, () -> bankAccountService.transferAmount(transferAmountRequest));
        assertEquals(ExceptionMessageConstants.ACCOUNT_DELETION.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw insufficient balance exception when amount is less than 0 while transferring amount.")
    void should_throw_InsufficientInitialBalanceException_transferAmount() {
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(123456L, 999999L, 5000.00);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getToAccountNumber())).thenReturn(secondAccount);
        when(bankAccountRepository.findByAccountNumber(transferAmountRequest.getFromAccountNumber())).thenReturn(bankAccount);
        InsufficientInitialBalanceException exception = assertThrows(InsufficientInitialBalanceException.class, () -> bankAccountService.transferAmount(transferAmountRequest));
        assertEquals(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError(), exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when request body has null values.")
    void should_throw_NumberFormatException_showAccountBalanceByAccountNumber() {
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> bankAccountService.showAccountBalanceByAccountNumber(null));
        assertEquals("Invalid type of data: Account number must be a number.", exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when request body has null values.")
    void should_throw_NumberFormatException_deleteBankAccount() {
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> bankAccountService.deleteBankAccount(null));
        assertEquals("Invalid type of data: Account number must be a number.", exception.getMessage());
    }

    @Test
    @DisplayName(value = "Should throw null argument exception when request body has null values.")
    void should_throw_NumberFormatException_getBankStatement() {
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> bankAccountService.getBankStatement(null));
        assertEquals("Invalid type of data: Account number must be a number.", exception.getMessage());
    }

}
