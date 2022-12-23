package com.cognologix.bankapplication.services.customers.impl;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.dto.UserDeletionResponse;
import com.cognologix.bankapplication.mappers.UserMapper;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.UserRepository;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserServiceImplTest.class})
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Mock
    BankAccountService bankAccountService;

    List<BankAccount> bankAccounts = new ArrayList<>();
    UserMapper userMapper = new UserMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName(value = "Should create user account on providing correct request body.")
    void test_createUserAccount() {
        UserAccountCreationRequest userAccountCreationRequest = new UserAccountCreationRequest("Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R");
        User user = new User(6, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());
        UserAccountCreationResponse expected = new UserAccountCreationResponse(user.getId(), Constants.ACCOUNT_CREATED.name(), user);

        when(userRepository.save(userMapper.maptoUserEntity(userAccountCreationRequest))).thenReturn(user);
        UserAccountCreationResponse actual = userService.createUserAccount(userAccountCreationRequest);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Should set userStatus to INACTIVE for given user id if exists.")
    void test_deleteUser() {
        User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());
        BankAccount bankAccount = new BankAccount(123456L, "Manjula", String.valueOf(LocalDateTime.now()), "Savings", "INR", "BR123", 2000.00, 1, Constants.ACTIVE.name(), user);
        BankAccount secondAccount = new BankAccount(999999L, "Manjula", String.valueOf(LocalDateTime.now()), "Current", "INR", "BR123", 5000.00, 1, Constants.ACTIVE.name(), user);
        BaseResponse baseResponse = new BaseResponse(Constants.USER_DELETION_SUCCESSFUL.name(), true);
        bankAccounts.add(bankAccount);
        bankAccounts.add(secondAccount);
        when(!userRepository.existsById(1)).thenReturn(true);
        when(bankAccountService.findByCustomerId(1)).thenReturn(bankAccounts);
        when(userRepository.findByIdEquals(1)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(bankAccountService.deleteBankAccount(bankAccount.getAccountNumber())).thenReturn(baseResponse);
        when(bankAccountService.deleteBankAccount(secondAccount.getAccountNumber())).thenReturn(baseResponse);
        when(userRepository.save(user)).thenReturn(user);
        UserDeletionResponse actual = userService.deleteUser(1);
        assertEquals(new UserDeletionResponse(Constants.USER_DELETION_SUCCESSFUL.name(), true), actual);
    }

    @Test
    @DisplayName(value = "Should fetch user account on providing existing user id.")
    void test_getUserById() {
        User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User actual = userService.getUserById(1);
        assertEquals(user, actual);
    }
}