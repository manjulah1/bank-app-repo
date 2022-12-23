package com.cognologix.bankapplication.mappers;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.dto.UserAccountCreationRequest;
import com.cognologix.bankapplication.models.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public User maptoUserEntity(UserAccountCreationRequest userAccountCreationRequest) {
        User user = new User();
        user.setCustomerName(userAccountCreationRequest.getCustomerName());
        user.setCustomerAddress(userAccountCreationRequest.getCustomerAddress());
        user.setCustomerCity(userAccountCreationRequest.getCustomerCity());
        user.setCustomerEmail(userAccountCreationRequest.getCustomerEmail());
        user.setCustomerState(userAccountCreationRequest.getCustomerState());
        user.setCustomerCountry(userAccountCreationRequest.getCustomerCountry());
        user.setCustomerPincode(userAccountCreationRequest.getCustomerPincode());
        user.setAadharNumber(userAccountCreationRequest.getAadharNumber());
        user.setPanNumber(userAccountCreationRequest.getPanNumber());
        user.setPhoneNumber(userAccountCreationRequest.getPhoneNumber());
        user.setUserStatus(Constants.ACTIVE.name());
        return user;
    }
}
