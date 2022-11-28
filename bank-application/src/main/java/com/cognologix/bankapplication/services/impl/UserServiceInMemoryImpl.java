package com.cognologix.bankapplication.services.impl;

import com.cognologix.bankapplication.dto.UsersListResponse;
import com.cognologix.bankapplication.exceptions.AccountDeletionException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ResourceNotFoundException;
import com.cognologix.bankapplication.inmemory.UserStorage;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.services.UserServiceInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceInMemoryImpl implements UserServiceInMemory {

    @Autowired
    private UserStorage userStorage;

    // service to create a user account in user storage
    // Input: user object without customerId (customerId will be auto generated)
    // Output: User object with auto-generated customerId
    @Override
    public User createUserAccountInMemory(User user) {
        try {
            if (user.getAadharNumber().equals("") || user.getPanNumber().equals("")) {
                throw new NullArgumentException("Argument missing.");
            } else {
                user.setId(userStorage.getAllUsers().size() + 1);
                user.setUserStatus("ACTIVE");
            }
        } catch (NullArgumentException nae) {
            System.out.println(nae.getMessage());
        }
        return userStorage.createUserAccountInMemory(user);
    }

    // service to get all users from users list in user storage
    @Override
    public UsersListResponse getAllUsers() {
        UsersListResponse usersListResponse = null;
        try {
            List<User> userList = userStorage.getAllUsers();
            usersListResponse.setTotalElements(userList.size());
            usersListResponse.setUserList(userList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return usersListResponse;
    }

    // service to delete a user from user storage
    // Input: Integer value of user id
    @Override
    public String deleteUser(Integer userId) {
        String message = "";
        try {
            if (userId == null) {
                throw new NullArgumentException("user id is not given in request.");
            } else if (userStorage.getUserById(userId) == null) {
                throw new ResourceNotFoundException("User not found.");
            } else {
                User user = userStorage.deleteUser(userId);
                if (user.getUserStatus().equals("ACTIVE")) {
                    throw new AccountDeletionException("User deletion failed.");
                } else {
                    message = "User deleted successfully.";
                }
            }
        } catch (ResourceNotFoundException | AccountDeletionException ex) {
            System.out.println(ex.getMessage());
        }
        return message;
    }

    // service to get a user
    // Input: user id
    // output: User object with all user details
    @Override
    public User getUserById(Integer userId) {
        Optional<Integer> id = Optional.ofNullable(userId);
        if (id.isEmpty()) {
            throw new NullArgumentException("user id is not given in request.");
        }
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NullPointerException("User not found");
        }
        return user;
    }
}
