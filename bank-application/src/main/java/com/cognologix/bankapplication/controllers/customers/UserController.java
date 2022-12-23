package com.cognologix.bankapplication.controllers.customers;

import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.dto.UserDeletionResponse;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.services.customers.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // controller - to create a user
    @PostMapping("/createAccount")
    public ResponseEntity<UserAccountCreationResponse> createUserAccountInMemory(@Valid @RequestBody UserAccountCreationRequest user) {
        UserAccountCreationResponse responseUser = userService.createUserAccount(user);
        HttpStatus status = responseUser != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(responseUser, status);
    }

    // controller - delete a user by user id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDeletionResponse> deleteUser(@NotNull @PathVariable("id") Integer id) {
        UserDeletionResponse userDeletionResponse = userService.deleteUser(id);
        HttpStatus status = Boolean.TRUE.equals(userDeletionResponse.getIsSuccess()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(userDeletionResponse, status);
    }

    // controller - fetch user by user id
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@NotNull @PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(user, status);
    }
}
