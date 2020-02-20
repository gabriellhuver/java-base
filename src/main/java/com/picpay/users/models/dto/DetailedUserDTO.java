package com.picpay.users.models.dto;

import com.picpay.users.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;

@Getter
@Setter
@SuperBuilder
@ToString
public class DetailedUserDTO {
    private User user;
    private HashMap<String,Object> accounts;
}
