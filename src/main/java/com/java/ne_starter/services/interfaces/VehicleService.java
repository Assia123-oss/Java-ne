package com.java.ne_starter.services.interfaces;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.vehicle.CreateVehicleDto;
import com.java.ne_starter.dtos.vehicle.VehicleResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleService {
    ResponseEntity<ApiResponse<VehicleResponseDto>> registerVehicle(CreateVehicleDto dto);
    ResponseEntity<ApiResponse<List<VehicleResponseDto>>> getVehiclesByOwner(Long ownerId);
}