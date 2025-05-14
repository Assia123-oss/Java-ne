package com.java.ne_starter.dtos.owner;

import lombok.Data;


@Data
public class OwnerResponseDto {
    private Long id;
    private String name;
    private String nationalId;
    private String email;
    private String phoneNumber;
    private String address;
}
