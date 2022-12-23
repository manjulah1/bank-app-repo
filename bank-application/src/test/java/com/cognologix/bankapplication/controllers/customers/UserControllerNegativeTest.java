package com.cognologix.bankapplication.controllers.customers;

import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import com.cognologix.bankapplication.controllers.AbstractTest;
import com.cognologix.bankapplication.exceptions.ErrorResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerNegativeTest extends AbstractTest {
    @Test
    @DisplayName(value = "DELETE USER - Should throw ResourceNotFoundException when user id is not available.")
    public void test_deleteUser_ResourceNotFoundException() throws Exception {
        this.setUp();
        String URI = "/users/delete/{id}";

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(URI, 3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getErrorCode(), ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "GET USER BY ID - Should throw ResourceNotFoundException when user id is not available.")
    public void test_getUserById_ResourceNotFoundException() throws Exception {
        this.setUp();
        String URI = "/users/user/{id}";

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI, 3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getErrorCode(), ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "GET USER BY ID - Should throw AccountDeletionException when user id is INACTIVE.")
    public void test_getUserById_AccountDeletionException() throws Exception {
        this.setUp();
        String URI = "/users/user/{id}";

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.ACCOUNT_DELETION.getErrorCode(), ExceptionMessageConstants.ACCOUNT_DELETION.getError());
        assertEquals(expected, actual);
    }

}
