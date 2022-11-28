package com.cognologix.bankapplication.controllers.accounts;

import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @RequestMapping(path = "/createBankAccount/{customer_id}/", method = RequestMethod.POST)
    public ResponseEntity<Object> createUsersBankAccount(@PathVariable Integer customer_id, @RequestBody BankAccount bankAccount) {
        try {
            BankAccountCreationResponse bankAccountCreationResponse = bankAccountService.createUsersBankAccount(customer_id, bankAccount);
            return new ResponseEntity<>(bankAccountCreationResponse, HttpStatus.OK);
        } catch (NullArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> depositAmount(){
        return null;
    }
}
