package com.java.ne_starter.controllers;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.vehicle.CreateVehicleDto;
import com.java.ne_starter.dtos.vehicle.VehicleResponseDto;
import com.java.ne_starter.services.interfaces.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<VehicleResponseDto>> registerVehicle(@Valid @RequestBody CreateVehicleDto dto) {
        return vehicleService.registerVehicle(dto);
    }

    @GetMapping("/by-owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<VehicleResponseDto>>> getVehiclesByOwner(@PathVariable Long ownerId) {
        return vehicleService.getVehiclesByOwner(ownerId);
    }
}