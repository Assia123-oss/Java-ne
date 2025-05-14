package com.java.ne_starter.dtos.plate;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlateNumberResponseDto {
    private Long id;
    private String plateNumber;
    private String status;
    private LocalDate issuedDate;

    private Long ownerId;
    private String ownerName;
}
