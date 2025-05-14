package com.java.ne_starter.dtos.user;


import lombok.Data;

import java.math.BigInteger;
import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String nationalId;
    private String phoneNumber;
    private Set<String> roles;
}
