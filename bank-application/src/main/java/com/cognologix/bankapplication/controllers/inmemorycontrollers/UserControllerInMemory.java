package com.cognologix.bankapplication.controllers.inmemorycontrollers;

import com.cognologix.bankapplication.dto.UsersListResponse;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.services.UserServiceInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/inmemory")
public class UserControllerInMemory {

    @Autowired
    UserServiceInMemory userServiceInMemory;

    // controller - to create a user
    @PostMapping("/createAccount")
    public ResponseEntity<User> createUserAccountInMemory(@Valid @RequestBody User user) {
        User responseUser = userServiceInMemory.createUserAccountInMemory(user);
        HttpStatus status = responseUser != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(responseUser, status);
    }

    // controller - show existing users
    @GetMapping("/users")
    public ResponseEntity<UsersListResponse> showUsers() {
        UsersListResponse usersListResponse = userServiceInMemory.getAllUsers();
        HttpStatus status = usersListResponse != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(usersListResponse, status);
    }

    // controller - delete a user by user id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        String message = userServiceInMemory.deleteUser(id);
        HttpStatus status = message.length() == 0 ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(message, status);
    }

    // controller - fetch user by user id
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userServiceInMemory.getUserById(id);
        HttpStatus status = user != null ? HttpStatus.OK: HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(user, status);
    }
}
