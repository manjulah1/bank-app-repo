package com.cognologix.bankapplication.controllers.inmemorycontrollers;

import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ResourceNotFoundException;
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

    @PostMapping("/createAccount")
    public ResponseEntity<Object> createUserAccountInMemory(@Valid @RequestBody User user) {
        try {
            User responseUser = userServiceInMemory.createUserAccountInMemory(user);
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        } catch (NullArgumentException nae) {
            return new ResponseEntity<>(nae.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> showUsers() {
        try {
            return new ResponseEntity<>(userServiceInMemory.getAllUsers(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
//        try {
//            if (userServiceInMemory.getUserById(id) == null) {
//                throw new ResourceNotFoundException("User not found");
//            }
//            userServiceInMemory.deleteUser(id);
//            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
//        } catch (NullArgumentException nae) {
//            return new ResponseEntity<>(nae.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id) {
        try {
            User user = userServiceInMemory.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBankAccount(@PathVariable("id") Integer userId) {
        String message = userServiceInMemory.deleteUser(userId);
        HttpStatus status = message.isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(message, status);
    }
}
