package com.cognologix.bankapplication.controllers.accounts;

import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import com.cognologix.bankapplication.controllers.AbstractTest;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.exceptions.ErrorResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountControllerNegativeTest extends AbstractTest {

    @Test
    public void test_createBankAccountController_NullArgumentException() throws Exception {
        this.setUp();
        String URI = "/accounts/createBankAccount";
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(null, 3000.00, "Kothrud", null);
        String inputJson = mapToJson(bankAccountCreationRequest);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.NULL_ARGUMENT.getErrorCode(), ExceptionMessageConstants.NULL_ARGUMENT.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_createBankAccountController_InsufficientInitialBalanceException() throws Exception {
        this.setUp();
        String URI = "/accounts/createBankAccount";
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest("Savings", 500.00, "Kothrud", 1);
        String inputJson = mapToJson(bankAccountCreationRequest);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getErrorCode(), ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_createBankAccountController_ResourceNotFoundException() throws Exception {
        this.setUp();
        String URI = "/accounts/createBankAccount";
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest("Savings", 1500.00, "Kothrud", 3);
        String inputJson = mapToJson(bankAccountCreationRequest);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getErrorCode(), ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_depositAmount_ZeroDepositionException() throws Exception {
        this.setUp();
        String URI = "/accounts/deposit";
        AmountRequest amountRequest = new AmountRequest(100000000009L, 0.00);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.ZERO_AMOUNT.getErrorCode(), ExceptionMessageConstants.ZERO_AMOUNT.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_depositAmount_NullArgumentException() throws Exception {
        this.setUp();
        String URI = "/accounts/deposit";
        AmountRequest amountRequest = new AmountRequest(null, 100.0);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.NULL_ARGUMENT.getErrorCode(), ExceptionMessageConstants.NULL_ARGUMENT.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_depositAmount_ResourceNotFoundException() throws Exception {
        this.setUp();
        String URI = "/accounts/deposit";
        AmountRequest amountRequest = new AmountRequest(100000000008L, 200.0);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getErrorCode(), ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_withdrawAmount_ZeroDepositionException() throws Exception {
        this.setUp();
        String URI = "/accounts/withdraw";
        AmountRequest amountRequest = new AmountRequest(100000000009L, 0.00);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.ZERO_AMOUNT.getErrorCode(), ExceptionMessageConstants.ZERO_AMOUNT.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_withdrawAmount_NullArgumentException() throws Exception {
        this.setUp();
        String URI = "/accounts/withdraw";
        AmountRequest amountRequest = new AmountRequest(null, 6000.00);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.NULL_ARGUMENT.getErrorCode(), ExceptionMessageConstants.NULL_ARGUMENT.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_withdrawAmount_InsufficientInitialBalanceException() throws Exception {
        this.setUp();
        String URI = "/accounts/withdraw";
        AmountRequest amountRequest = new AmountRequest(100000000009L, 6000.00);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getErrorCode(), ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_transferAmount_ZeroDepositionException() throws Exception {
        this.setUp();
        String URI = "/accounts/transferAmount";
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(100000000008L, 100000000009L, 0.00);
        String inputJson = mapToJson(transferAmountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content11, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.ZERO_AMOUNT.getErrorCode(), ExceptionMessageConstants.ZERO_AMOUNT.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_transferAmount_fromAccount_AccountDeletionException() throws Exception {
        this.setUp();
        String URI = "/accounts/transferAmount";
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(100000000008L, 100000000009L, 300.00);

        String inputJson = mapToJson(transferAmountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.ACCOUNT_DELETION.getErrorCode(), ExceptionMessageConstants.ACCOUNT_DELETION.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_transferAmount_toAccount_AccountDeletionException() throws Exception {
        this.setUp();
        String URI = "/accounts/transferAmount";
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(100000000009L, 100000000008L, 300.00);

        String inputJson = mapToJson(transferAmountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.ACCOUNT_DELETION.getErrorCode(), ExceptionMessageConstants.ACCOUNT_DELETION.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_transferAmount_InsufficientInitialBalanceException() throws Exception {
        this.setUp();
        String URI = "/accounts/transferAmount";
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(100000000009L, 100000000002L, 3191.00);

        String inputJson = mapToJson(transferAmountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getErrorCode(), ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError());
        assertEquals(expected, actual);
    }

    @Test
    public void test_showAccountBalanceByAccountNumber_NumberFormatException() throws Exception {
        this.setUp();
        String URI = "/accounts/showBalance";

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", String.valueOf((Object) null))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), actual.getMessage());
        assertEquals(expected, actual);
    }

    @Test
    public void test_deleteBankAccount_NumberFormatException() throws Exception {
        this.setUp();
        String URI = "/accounts/delete";

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", String.valueOf((Object) null))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), actual.getMessage());
        assertEquals(expected, actual);
    }

    @Test
    public void test_getStatement_NumberFormatException() throws Exception {
        this.setUp();
        String URI = "/accounts/getStatement";

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", String.valueOf((Object) null))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse actual = this.mapFromJson(content, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), actual.getMessage());
        assertEquals(expected, actual);
    }

}
