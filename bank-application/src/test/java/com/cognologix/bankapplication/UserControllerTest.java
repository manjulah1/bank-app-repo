package com.cognologix.bankapplication;

import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.UserRepository;
import com.cognologix.bankapplication.services.UserServiceInMemory;
import com.cognologix.bankapplication.services.customers.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest extends BankApplicationTests {

//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UserRepository userRepository;

    @Autowired
    UserServiceInMemory userServiceInMemory;

    @Test
    public void testCreateUserAccountRequest() {
        User user = new User();
        user.setCustomerName("TestNameSample");
        user.setCustomerAddress("Test Address");
        user.setCustomerCity("Test City");
        user.setCustomerState("Test State");
        user.setCustomerCountry("Test Country");
        user.setCustomerPincode("123456");
        user.setCustomerEmail("test@gmail.com");
        user.setPhoneNumber("9999999999");
        user.setAadharNumber("123456782012");
        user.setPanNumber("SDFG1234L");
//        userService.createUserAccount(user);
        User createdUser = userServiceInMemory.createUserAccountInMemory(user);
        user.setId(createdUser.getId());
        Assertions.assertEquals(user, createdUser);
//        assertNotNull(userServiceInMemory.getUserById(createdUser.getId()));
    }
}
