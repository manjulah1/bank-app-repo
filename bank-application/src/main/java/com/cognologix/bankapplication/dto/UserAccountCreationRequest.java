package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountCreationRequest {

    private String customerName;
    private String customerAddress;
    private String customerCity;
    private String customerState;
    private String customerCountry;
    private String customerPincode;
    private String customerEmail;
    private String phoneNumber;
    private String aadharNumber;
    private String panNumber;

}
