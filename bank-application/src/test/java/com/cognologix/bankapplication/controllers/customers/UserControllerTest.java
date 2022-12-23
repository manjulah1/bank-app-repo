package com.cognologix.bankapplication.controllers.customers;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.controllers.AbstractTest;
import com.cognologix.bankapplication.dto.AccountBalanceResponse;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.dto.UserAccountCreationResponse;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.services.customers.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends AbstractTest {

    @Autowired
    UserService userService;
    @Test
    void createUserAccountInMemory() throws Exception {
        this.setUp();
        String URI = "/users/createAccount";

        UserAccountCreationRequest user = new UserAccountCreationRequest("Onkar", "Gokhlenagar", "Pune", "Maharashtra", "India", "411122", "onkar@gmail.com", "9999999999", "123456789111", "ANHFD1234L");
        Integer recordCount = userService.getAllUsers().size();
        User expectedUser = new User(recordCount+1, "Onkar", "Gokhlenagar", "Pune", "Maharashtra", "India", "411122", "onkar@gmail.com", "9999999999", "123456789111", "ANHFD1234L", Constants.ACTIVE.name());
        UserAccountCreationResponse userAccountCreationResponse = new UserAccountCreationResponse(recordCount+1, Constants.ACCOUNT_CREATED.name(), expectedUser);
        String expected = mapToJson(userAccountCreationResponse);

        String inputJson = mapToJson(user);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String actual = mvcResult.getResponse().getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    void deleteUser() throws Exception {
        this.setUp();
        String URI = "/users/delete/{id}";
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(URI, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        BaseResponse actual = this.mapFromJson(content11, BaseResponse.class);
        BaseResponse expected = new BaseResponse(Constants.USER_DELETION_SUCCESSFUL.name(), true);
        assertEquals(expected, actual);
    }

    @Test
    void getUserById() throws Exception {
        this.setUp();
        String URI = "/users/user/{id}";
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        User actual = this.mapFromJson(content11, User.class);
        User expected = new User(1,"Manjula", "Kothrud", "Pune", "Maharashtra", "India", "411112", "manjula@gmail.com", "8898987333", "999123434477", "ANKPH3333L", "ACTIVE");
        assertEquals(expected, actual);
    }
}