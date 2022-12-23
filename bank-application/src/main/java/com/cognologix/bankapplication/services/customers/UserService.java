package com.cognologix.bankapplication.services.customers;

import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.dto.UserDeletionResponse;
import com.cognologix.bankapplication.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserAccountCreationResponse createUserAccount(UserAccountCreationRequest user);

    UserDeletionResponse deleteUser(Integer userId);

    User getUserById(Integer userId);

    List<User> getAllUsers();
}
