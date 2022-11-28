package com.cognologix.bankapplication.dto;

import com.cognologix.bankapplication.models.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class UsersListResponse {

    private Integer totalElements;

    private List<User> userList;
}
