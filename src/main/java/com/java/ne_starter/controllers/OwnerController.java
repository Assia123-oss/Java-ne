package com.java.ne_starter.controllers;

import com.java.ne_starter.dtos.owner.CreateOwnerDto;
import com.java.ne_starter.dtos.owner.OwnerResponseDto;
import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.services.interfaces.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<OwnerResponseDto>> registerOwner(@Valid @RequestBody CreateOwnerDto dto) {
        return ownerService.createOwner(dto);
    }

    @GetMapping("/get-by-national-id/{nationalId}")
    public ResponseEntity<ApiResponse<OwnerResponseDto>> getByNationalId(@PathVariable String nationalId) {
        return ownerService.getOwnerByNationalId(nationalId);
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<ApiResponse<OwnerResponseDto>> getByEmail(@PathVariable String email) {
        return ownerService.getOwnerByEmail(email);
    }

    @GetMapping("/get-by-phone/{phone}")
    public ResponseEntity<ApiResponse<OwnerResponseDto>> getByPhone(@PathVariable String phone) {
        return ownerService.getOwnerByPhone(phone);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<OwnerResponseDto>>> getAllOwners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ownerService.getAllOwners(page, size);
    }
}

