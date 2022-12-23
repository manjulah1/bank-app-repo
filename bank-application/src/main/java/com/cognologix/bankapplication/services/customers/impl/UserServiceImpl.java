package com.cognologix.bankapplication.services.customers.impl;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.dto.UserDeletionResponse;
import com.cognologix.bankapplication.exceptions.AccountDeletionException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ResourceNotFoundException;
import com.cognologix.bankapplication.exceptions.handlers.UserExceptionHandler;
import com.cognologix.bankapplication.mappers.UserMapper;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.UserRepository;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
import com.cognologix.bankapplication.services.customers.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Autowired
    private BankAccountService bankAccountService;

    private final UserExceptionHandler userExceptionHandler = new UserExceptionHandler();

    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserAccountCreationResponse createUserAccount(UserAccountCreationRequest user) {
        UserAccountCreationResponse userAccountCreationResponse = new UserAccountCreationResponse();
        try {
            if (Boolean.TRUE.equals(userExceptionHandler.checkNullArgumentsForCreateUser(user))) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            } else {
                User savedUser = userRepository.save(userMapper.maptoUserEntity(user));
                userAccountCreationResponse.setCustomerId(savedUser.getId());
                userAccountCreationResponse.setSuccessMessage(String.valueOf(Constants.ACCOUNT_CREATED));
                userAccountCreationResponse.setAdditionalInformation(savedUser);
            }
        } catch (NullArgumentException nae) {
            throw new NullArgumentException(nae.getMessage());
        }
        return userAccountCreationResponse;
    }

    @Override
    public UserDeletionResponse deleteUser(Integer userId) {
        try {
            if (userId == null) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            } else if (!userRepository.existsById(userId)) {
                throw new ResourceNotFoundException(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
            } else {
                List<BankAccount> bankAccounts = bankAccountService.findByCustomerId(userId);
                Optional<User> user = userRepository.findById(userId);
                if (bankAccounts.isEmpty() && user.isPresent()) {
                    user.get().setUserStatus(String.valueOf(Constants.INACTIVE));
                    userRepository.save(user.get());
                } else {
                    boolean isAccountDeleted = false;
                    for (BankAccount bankAccount :
                            bankAccounts) {
                        isAccountDeleted = bankAccountService.deleteBankAccount(bankAccount.getAccountNumber()).getIsSuccess();
                    }
                    if (isAccountDeleted && user.isPresent()) {
                        user.get().setUserStatus(String.valueOf(Constants.INACTIVE));
                        userRepository.save(user.get());
                    }
                }
                UserDeletionResponse userDeletionResponse = new UserDeletionResponse();
                if (user.isPresent() && user.get().getUserStatus().equals(String.valueOf(Constants.ACTIVE))) {
                    userDeletionResponse.setIsSuccess(false);
                    userDeletionResponse.setMessage(Constants.USER_DELETION_FAILED.name());
                } else {
                    userDeletionResponse.setIsSuccess(true);
                    userDeletionResponse.setMessage(Constants.USER_DELETION_SUCCESSFUL.name());
                }
                return userDeletionResponse;
            }
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (NullArgumentException nae) {
            throw new NullArgumentException(nae.getMessage());
        }
    }

    @Override
    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
        } else if (user.get().getUserStatus().equals(Constants.INACTIVE.name())) {
            throw new AccountDeletionException(ExceptionMessageConstants.ACCOUNT_DELETION.getError());
        } else {
            return user.get();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
