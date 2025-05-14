package com.java.ne_starter.services.implementations;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.vehicle.CreateVehicleDto;
import com.java.ne_starter.dtos.vehicle.VehicleResponseDto;
import com.java.ne_starter.exceptions.NotFoundException;
import com.java.ne_starter.models.Owner;
import com.java.ne_starter.models.Vehicle;
import com.java.ne_starter.repositories.OwnerRepository;
import com.java.ne_starter.repositories.VehicleRepository;
import com.java.ne_starter.services.interfaces.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final OwnerRepository ownerRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity<ApiResponse<VehicleResponseDto>> registerVehicle(CreateVehicleDto dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setColor(dto.getColor());
        vehicle.setOwner(owner);
        vehicleRepository.save(vehicle);

        VehicleResponseDto response = new VehicleResponseDto();
        BeanUtils.copyProperties(vehicle, response);
        response.setOwnerId(owner.getId());
        response.setOwnerName(owner.getName());

        return ApiResponse.success("Vehicle registered successfully", HttpStatus.CREATED, response);
    }

    @Override
    public ResponseEntity<ApiResponse<List<VehicleResponseDto>>> getVehiclesByOwner(Long ownerId) {
        List<Vehicle> vehicles = vehicleRepository.findByOwnerId(ownerId);

        List<VehicleResponseDto> responseList = vehicles.stream().map(vehicle -> {
            VehicleResponseDto dto = new VehicleResponseDto();
            BeanUtils.copyProperties(vehicle, dto);
            dto.setOwnerId(vehicle.getOwner().getId());
            dto.setOwnerName(vehicle.getOwner().getName());
            return dto;
        }).collect(Collectors.toList());

        return ApiResponse.success("Vehicles fetched successfully", HttpStatus.OK, responseList);
    }
}