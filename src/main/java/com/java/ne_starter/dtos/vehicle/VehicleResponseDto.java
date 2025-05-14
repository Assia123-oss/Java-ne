package com.java.ne_starter.dtos.vehicle;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleResponseDto {
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private String chassisNumber;
    private Long ownerId;
    private Long plateNumberId;
    private BigDecimal price;
    private String ownerName;
}