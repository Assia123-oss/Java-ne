package com.java.ne_starter.controllers;


import com.java.ne_starter.dtos.plate.CreatePlateNumberDto;
import com.java.ne_starter.dtos.plate.PlateNumberResponseDto;
import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.services.interfaces.PlateNumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plates")
@RequiredArgsConstructor
public class PlateNumberController {

    private final PlateNumberService plateNumberService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<PlateNumberResponseDto>> registerPlate(@Valid @RequestBody CreatePlateNumberDto dto) {
        return plateNumberService.registerPlate(dto);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<PlateNumberResponseDto>>> getByOwner(@PathVariable Long ownerId) {
        return plateNumberService.getPlatesByOwner(ownerId);
    }

    @GetMapping("/search/{plateNumber}")
    public ResponseEntity<ApiResponse<PlateNumberResponseDto>> getByPlate(@PathVariable String plateNumber) {
        return plateNumberService.getPlateByNumber(plateNumber);
    }
}
