package com.java.ne_starter.dtos.plate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePlateNumberDto {
    @NotBlank
    private String plateNumber;


    @NotNull
    private Long ownerId;

    @NotNull
    private LocalDate issuedDate;
}