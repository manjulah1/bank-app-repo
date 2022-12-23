package com.cognologix.bankapplication.controllers.accounts;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.controllers.AbstractTest;
import com.cognologix.bankapplication.dto.AccountBalanceResponse;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.dto.BankStatementResponse;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.DepositAmountResponse;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.dto.TransferAmountResponse;
import com.cognologix.bankapplication.dto.WithdrawAmountResponse;
import com.cognologix.bankapplication.repositories.TransactionRepository;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BankAccountControllerTest extends AbstractTest {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void test_createUsersBankAccountController() throws Exception {
        this.setUp();
        String URI = "/accounts/createBankAccount";
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest("Savings", 3000.00, "Kothrud", 1);

        BankAccountCreationResponse bankAccountCreationResponse = new BankAccountCreationResponse();
        int recordCount = bankAccountService.getAllBankAccounts().size();
        bankAccountCreationResponse.setGeneratedAccountNumber(100000000000L + recordCount);
        bankAccountCreationResponse.setIsSuccess(true);
        bankAccountCreationResponse.setMessage(Constants.ACCOUNT_CREATED.name());
        String expected = mapToJson(bankAccountCreationResponse);
        String inputJson = mapToJson(bankAccountCreationRequest);

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
    public void test_depositAmountController() throws Exception {
        this.setUp();
        String URI = "/accounts/deposit";
        AmountRequest amountRequest = new AmountRequest(100000000008L, 200.00);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        DepositAmountResponse actual = this.mapFromJson(content11, DepositAmountResponse.class);
        DepositAmountResponse expected = new DepositAmountResponse(Constants.AMOUNT_DEPOSITED.name(), true, 100000000008L, actual.getDepositedAmount(), actual.getDepositDate());
        assertEquals(expected, actual);
    }

    @Test
    public void test_withdrawAmountController() throws Exception {
        this.setUp();
        String URI = "/accounts/withdraw";
        AmountRequest amountRequest = new AmountRequest(100000000008L, 200.00);

        String inputJson = mapToJson(amountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        WithdrawAmountResponse actual = this.mapFromJson(content11, WithdrawAmountResponse.class);
        WithdrawAmountResponse expected = new WithdrawAmountResponse(Constants.AMOUNT_WITHDRAWAL.name(), true, 100000000008L, actual.getWithdrawalDate(), actual.getAccountBalance());
        assertEquals(expected, actual);
    }

    @Test
    public void test_transferAmountContoller() throws Exception {
        this.setUp();
        String URI = "/accounts/transferAmount";
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest(100000000008L, 100000000002L, 600.00);

        String inputJson = mapToJson(transferAmountRequest);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        TransferAmountResponse actual = this.mapFromJson(content11, TransferAmountResponse.class);
        TransferAmountResponse expected = new TransferAmountResponse(Constants.AMOUNT_DEPOSITED.name(), true, 100000000008L, 100000000002L, actual.getTransactionAmount());
        assertEquals(expected, actual);
    }

    @Test
    public void test_showAccountBalanceController() throws Exception {
        this.setUp();
        String URI = "/accounts/showBalance";
        Long accountNumber = 100000000008L;
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", String.valueOf(accountNumber))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        AccountBalanceResponse actual = this.mapFromJson(content11, AccountBalanceResponse.class);
        AccountBalanceResponse expected = new AccountBalanceResponse(Constants.SHOW_BALANCE_SUCCESSFUL.name(), true, 3100.0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_deleteBankAccountController() throws Exception {
        this.setUp();
        String URI = "/accounts/delete";
        Long accountNumber = 100000000008L;
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", String.valueOf(accountNumber))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        BaseResponse actual = this.mapFromJson(content11, BaseResponse.class);
        BaseResponse expected = new BaseResponse(Constants.BANK_ACCOUNT_DELETION_SUCCESSFUL.name(), true);
        assertEquals(expected, actual);
    }

    @Test
    public void getStatement() throws Exception {
        this.setUp();
        String URI = "/accounts/getStatement";
        Long accountNumber = 100000000008L;
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", String.valueOf(accountNumber))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content11 = mvcResult.getResponse().getContentAsString();
        BankStatementResponse[] actual = this.mapFromJson(content11, BankStatementResponse[].class);
        assertTrue(actual.length > 0);
    }
}