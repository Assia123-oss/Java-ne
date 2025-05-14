package com.java.ne_starter.services.interfaces;

import com.java.ne_starter.dtos.plate.CreatePlateNumberDto;
import com.java.ne_starter.dtos.plate.PlateNumberResponseDto;
import com.java.ne_starter.dtos.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlateNumberService {
    ResponseEntity<ApiResponse<PlateNumberResponseDto>> registerPlate(CreatePlateNumberDto dto);
    ResponseEntity<ApiResponse<List<PlateNumberResponseDto>>> getPlatesByOwner(Long ownerId);
    ResponseEntity<ApiResponse<PlateNumberResponseDto>> getPlateByNumber(String plateNumber);
}