package com.cognologix.bankapplication.services.accounts.impl;

import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.exceptions.InsufficientInitialBalanceException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.BankAccountRepository;
import com.cognologix.bankapplication.repositories.UserRepository;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public BankAccountCreationResponse createUsersBankAccount(Integer customer_id, BankAccount bankAccount) {

        BankAccountCreationResponse response = new BankAccountCreationResponse();
        if (customer_id == null || bankAccount.getAccountType().length() == 0) {
            throw new NullArgumentException("Request parameters are missing: Invalid Request");
        } else if (bankAccount.getAccountBalance() < 1000) {
            throw new InsufficientInitialBalanceException("Initial balance is less than ₹1000");
        } else {
            BankAccount bankAccount1 = new BankAccount();
            bankAccount1.setAccountCreationDate(LocalDate.now());
            bankAccount1.setAccountHolderName(bankAccount.getAccountHolderName());
            bankAccount1.setAccountType(bankAccount.getAccountType());
            bankAccount1.setBranchName(bankAccount.getBranchName());
            bankAccount1.setIfscCode(bankAccount.getIfscCode());
            bankAccount1.setAccountBalance(bankAccount.getAccountBalance());
            bankAccount1.setAccountNumber(Long.valueOf(UUID.randomUUID().toString()));
            Optional<User> user =  userRepository.findById(customer_id);
//            bankAccount1.setUser(user.get());

            BankAccount bankAccount2 = bankAccountRepository.save(bankAccount1);
            if (bankAccount2.getAccountNumber() != null && userRepository.findById(customer_id).isPresent()) {
//                response.setGeneratedAccountNumber(bankAccount2.getAccountNumber());
                response.setMessage(bankAccount2.getAccountType() + "Account created Successfully.");
            }
            return response;
        }
    }

}
