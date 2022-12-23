package com.cognologix.bankapplication.dto;

import com.cognologix.bankapplication.models.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserAccountCreationResponse {
    private Integer customerId;
    private String successMessage;
    private User additionalInformation;
}
