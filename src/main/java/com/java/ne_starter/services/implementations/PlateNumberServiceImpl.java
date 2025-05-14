package com.java.ne_starter.services.implementations;

import com.java.ne_starter.dtos.plate.CreatePlateNumberDto;
import com.java.ne_starter.dtos.plate.PlateNumberResponseDto;
import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.exceptions.ConflictException;
import com.java.ne_starter.exceptions.NotFoundException;
import com.java.ne_starter.models.Owner;
import com.java.ne_starter.models.PlateNumber;
import com.java.ne_starter.repositories.OwnerRepository;
import com.java.ne_starter.repositories.PlateNumberRepository;
import com.java.ne_starter.services.interfaces.PlateNumberService;
import com.java.ne_starter.enumerations.PlateStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlateNumberServiceImpl implements PlateNumberService {

    private final PlateNumberRepository plateNumberRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public ResponseEntity<ApiResponse<PlateNumberResponseDto>> registerPlate(CreatePlateNumberDto dto) {
        // Check if plate already exists
        if (plateNumberRepository.findByPlateNumber(dto.getPlateNumber()) != null) {
            throw new ConflictException("Plate number already exists");
        }

        // Ensure owner exists
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        // Create and save plate
        PlateNumber plate = new PlateNumber();
        plate.setPlateNumber(dto.getPlateNumber());
        plate.setOwner(owner);
        plate.setIssuedDate(dto.getIssuedDate());
        plate.setStatus(PlateStatus.AVAILABLE);

        plateNumberRepository.save(plate);

        // Build response DTO
        PlateNumberResponseDto response = new PlateNumberResponseDto();
        response.setId(plate.getId());
        response.setPlateNumber(plate.getPlateNumber());
        response.setStatus(plate.getStatus().name());
        response.setOwnerId(owner.getId());
        response.setOwnerName(owner.getName());
        response.setIssuedDate(plate.getIssuedDate());

        return ApiResponse.success("Plate number registered", HttpStatus.CREATED, response);
    }



    public ResponseEntity<ApiResponse<List<PlateNumberResponseDto>>> getPlatesByOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        List<PlateNumber> plates = plateNumberRepository.findByOwnerId(ownerId);

        List<PlateNumberResponseDto> responses = plates.stream().map(plate -> {
            PlateNumberResponseDto dto = new PlateNumberResponseDto();
            dto.setId(plate.getId());
            dto.setPlateNumber(plate.getPlateNumber());
            dto.setStatus(plate.getStatus().name());
            dto.setOwnerId(owner.getId());
            dto.setOwnerName(owner.getName());
            dto.setIssuedDate(plate.getIssuedDate());
            return dto;
        }).collect(Collectors.toList());

        return ApiResponse.success("Plate numbers for owner fetched", HttpStatus.OK, responses);
    }

    @Override
    public ResponseEntity<ApiResponse<PlateNumberResponseDto>> getPlateByNumber(String plateNumber) {
        PlateNumber plate = plateNumberRepository.findByPlateNumber(plateNumber);
        if (plate == null) {
            throw new NotFoundException("Plate number not found");
        }

        PlateNumberResponseDto dto = new PlateNumberResponseDto();
        dto.setId(plate.getId());
        dto.setPlateNumber(plate.getPlateNumber());
        dto.setStatus(plate.getStatus().name());
        dto.setOwnerId(plate.getOwner().getId());
        dto.setOwnerName(plate.getOwner().getName());
        dto.setIssuedDate(plate.getIssuedDate());

        return ApiResponse.success("Plate number found", HttpStatus.OK, dto);
    }
}
