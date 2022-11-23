package com.cognologix.bankapplication.services.customers;

import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserAccountCreationResponse createUserAccount(User user);
}
