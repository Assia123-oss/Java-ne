package com.java.ne_starter.dtos.transfer;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VehicleTransferResponseDto {
    private Long id;
    private Long vehicleId;
    private String previousOwnerName;
    private String newOwnerName;
    private String oldPlateNumber;
    private String newPlateNumber;
    private BigDecimal price;
    private LocalDateTime transferDate;
}
