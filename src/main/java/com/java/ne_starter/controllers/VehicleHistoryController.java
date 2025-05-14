package com.java.ne_starter.controllers;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.transfer.VehicleTransferResponseDto;
import com.java.ne_starter.services.interfaces.VehicleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class  VehicleHistoryController {

    private final VehicleHistoryService vehicleHistoryService;

    @GetMapping("/chassis/{chassisNumber}")
    public ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> getByChassis(@PathVariable String chassisNumber) {
        return vehicleHistoryService.getHistoryByChassis(chassisNumber);
    }

    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> getByPlate(@PathVariable String plateNumber) {
        return vehicleHistoryService.getHistoryByPlate(plateNumber);
    }
}
