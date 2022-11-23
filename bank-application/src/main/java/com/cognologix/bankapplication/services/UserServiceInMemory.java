package com.cognologix.bankapplication.services;

import com.cognologix.bankapplication.dto.UsersListResponse;
import com.cognologix.bankapplication.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInMemory {

    User createUserAccountInMemory(User user);

    UsersListResponse getAllUsers();

    String deleteUser(Integer userId);

    User getUserById(Integer userId);
}
