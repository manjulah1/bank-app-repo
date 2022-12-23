package com.cognologix.bankapplication.exceptions.handlers;

import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserExceptionHandler {
    public Boolean checkNullArgumentsForCreateUser(UserAccountCreationRequest user) {
        return user.getCustomerName() == null || user.getCustomerEmail() == null || user.getCustomerAddress() == null || user.getCustomerCity() == null || user.getCustomerState() == null || user.getCustomerCountry() == null || user.getCustomerPincode() == null
                || user.getPanNumber() == null || user.getAadharNumber() == null || user.getPhoneNumber() == null;
    }
}
