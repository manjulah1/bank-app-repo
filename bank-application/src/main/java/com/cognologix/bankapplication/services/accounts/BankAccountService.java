package com.cognologix.bankapplication.services.accounts;

import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.models.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface BankAccountService {
    BankAccountCreationResponse createUsersBankAccount(Integer customer_id, BankAccount bankAccount);

}
