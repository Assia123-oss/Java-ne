package com.java.ne_starter.dtos.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateVehicleDto {
    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @NotNull
    private Integer year;

    @NotBlank
    private String color;

    @NotNull
    private String chassisNumber;

    @NotNull
    private Long ownerId;

    @NotNull
    private Long plateNumberId; // A plate from the owner's list

    @NotNull
    private BigDecimal price;
}
