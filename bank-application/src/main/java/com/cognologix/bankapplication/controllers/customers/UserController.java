package com.cognologix.bankapplication.controllers.customers;

import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.services.customers.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/createAccount")
    public ResponseEntity<Object> createUserAccountRequest(@RequestBody User user) {
        try {
            UserAccountCreationResponse userAccountCreationResponse = userService.createUserAccount(user);
            return new ResponseEntity<>(userAccountCreationResponse,HttpStatus.OK);
        } catch (NullArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
