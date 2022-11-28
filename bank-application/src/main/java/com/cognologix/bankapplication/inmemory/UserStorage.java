package com.cognologix.bankapplication.inmemory;

import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserStorage {
    private List<User> users = new ArrayList<>();

    @Autowired
    BankAccountStorage bankAccountStorage;

    // create a user account
    public User createUserAccountInMemory(User user) {
        users.add(user);
        List<User> userList = users.stream().filter(user1 -> user1.getAadharNumber().equals(user.getAadharNumber())).collect(Collectors.toList());
        return userList.get(0);
    }

    // fetch all users from users list
    public List<User> getAllUsers() {
        return users;
    }

    // delete a user from users list
    public User deleteUser(Integer userId) {
        String message = "";
        List<BankAccount> bankAccounts = bankAccountStorage.getAccountsByCustomerId(userId);
        if (bankAccounts.size() == 0) {
            users = users.stream()
                    .peek(user -> {
                        if (user.getId().equals(userId)) {
                            user.setUserStatus("INACTIVE");
                        }
                    }).collect(Collectors.toList());
        } else {
            bankAccounts = bankAccounts.stream()
                    .map(bankAccount -> bankAccountStorage.deleteAccount(bankAccount.getAccountNumber())).collect(Collectors.toList());
            users = users.stream()
                    .peek(user -> {
                        if (user.getId().equals(userId)) {
                            user.setUserStatus("INACTIVE");
                        }
                    }).collect(Collectors.toList());
        }
        return getUserById(userId);
    }

    // get a user by giving user id as input and get user object in response
    public User getUserById(Integer userId) {
        List<User> user = users.stream().filter(user1 -> user1.getId().equals(userId))
                .collect(Collectors.toList());
        if (user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }
}
