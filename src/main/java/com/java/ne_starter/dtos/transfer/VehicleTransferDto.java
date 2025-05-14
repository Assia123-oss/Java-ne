package com.java.ne_starter.dtos.transfer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleTransferDto {
    @NotNull
    private Long vehicleId;

    @NotNull
    private Long newOwnerId;

    @NotNull
    private Long newPlateId;

    @NotNull
    private BigDecimal price;
}