package com.cognologix.bankapplication.services.customers.impl;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.exceptions.AccountDeletionException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ResourceNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserServiceImplNegativeTest.class})
public class UserServiceImplNegativeTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Mock
    BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName(value = "should throw NullArgumentException if request body contains null values.")
    void test_NullArgumentException_createUserAccount() {
        UserAccountCreationRequest userAccountCreationRequest = new UserAccountCreationRequest(null, null, null, null, null, null, null, null, null, null);
        NullArgumentException actualException = assertThrows(NullArgumentException.class, () -> {
            userService.createUserAccount(userAccountCreationRequest);
        });
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), actualException.getMessage());
    }

    @Test
    @DisplayName(value = "should throw NullArgumentException if userId is null.")
    void test_NullArgumentException_deleteUser() {
        NullArgumentException actualException = assertThrows(NullArgumentException.class, () -> {
            userService.deleteUser(null);
        });
        assertEquals(ExceptionMessageConstants.NULL_ARGUMENT.getError(), actualException.getMessage());
    }

    @Test
    @DisplayName(value = "should throw ResourceNotFoundException if userId is null.")
    void test_ResourceNotFoundException_deleteUser() {
        User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());
        when(!userRepository.existsById(1)).thenReturn(true);
        ResourceNotFoundException actualException = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(2);
        });
        assertEquals(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError(), actualException.getMessage());
    }

    @Test
    @DisplayName(value = "should throw ResourceNotFoundException if user does not exist.")
    void test_ResourceNotFoundException_getUserById() {
        User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.ACTIVE.name());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        ResourceNotFoundException actualException = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(2);
        });
        assertEquals(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError(), actualException.getMessage());
    }

    @Test
    @DisplayName(value = "should throw AccountDeletionException if userStatus is INACTIVE.")
    void test_AccountDeletionException_getUserById() {
        User user = new User(1, "Manjula", "Address", "City", "State", "Country", "411111", "manjula@gmail.com", "9090909090", "123456789012", "ANHFD1234R", Constants.INACTIVE.name());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        AccountDeletionException actualException = assertThrows(AccountDeletionException.class, () -> {
            userService.getUserById(1);
        });
        assertEquals(ExceptionMessageConstants.ACCOUNT_DELETION.getError(), actualException.getMessage());
    }

}
