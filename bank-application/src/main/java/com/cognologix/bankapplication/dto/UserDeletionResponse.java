package com.cognologix.bankapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class UserDeletionResponse extends BaseResponse {
    public UserDeletionResponse(String message, Boolean isSuccess) {
        super(message, isSuccess);
    }
}
