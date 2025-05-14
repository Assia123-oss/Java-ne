package com.java.ne_starter.services.interfaces;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.transfer.VehicleTransferResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleHistoryService {
    ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> getHistoryByChassis(String chassisNumber);
    ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> getHistoryByPlate(String plateNumber);
}