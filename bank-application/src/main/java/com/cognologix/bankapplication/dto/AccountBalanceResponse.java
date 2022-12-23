package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponse extends BaseResponse{
    Double accountBalance;

    public AccountBalanceResponse(String message, Boolean isSuccess, Double accountBalance) {
        super(message, isSuccess);
        this.accountBalance = accountBalance;
    }
}
