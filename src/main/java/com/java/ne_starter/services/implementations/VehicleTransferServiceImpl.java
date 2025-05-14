package com.java.ne_starter.services.implementations;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.transfer.VehicleTransferDto;
import com.java.ne_starter.dtos.transfer.VehicleTransferResponseDto;
import com.java.ne_starter.enumerations.PlateStatus;
import com.java.ne_starter.exceptions.NotFoundException;
import com.java.ne_starter.models.*;
import com.java.ne_starter.repositories.*;
import com.java.ne_starter.services.interfaces.VehicleTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VehicleTransferServiceImpl implements VehicleTransferService {

    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;
    private final PlateNumberRepository plateNumberRepository;
    private final VehicleTransferRepository transferRepository;

    @Override
    public ResponseEntity<ApiResponse<VehicleTransferResponseDto>> transferVehicle(VehicleTransferDto dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        Owner newOwner = ownerRepository.findById(dto.getNewOwnerId())
                .orElseThrow(() -> new NotFoundException("New owner not found"));

        PlateNumber newPlate = plateNumberRepository.findById(dto.getNewPlateId())
                .orElseThrow(() -> new NotFoundException("New plate not found"));

        if (newPlate.getStatus() != PlateStatus.AVAILABLE) {
            throw new IllegalStateException("Selected plate number is already in use");
        }

        PlateNumber oldPlate = vehicle.getVehiclePlateNumber();
        if (oldPlate != null) {
            oldPlate.setStatus(PlateStatus.AVAILABLE);
            plateNumberRepository.save(oldPlate);
        }

        newPlate.setStatus(PlateStatus.IN_USE);
        newPlate.setVehicle(vehicle);
        plateNumberRepository.save(newPlate);

        Owner previousOwner = vehicle.getOwner();
        vehicle.setOwner(newOwner);
        vehicle.setVehiclePlateNumber(newPlate);
        vehicleRepository.save(vehicle);

        VehicleTransfer transfer = new VehicleTransfer();
        transfer.setVehicle(vehicle);
        transfer.setPreviousOwner(previousOwner);
        transfer.setNewOwner(newOwner);
        transfer.setOldPlate(oldPlate);
        transfer.setNewPlate(newPlate);
        transfer.setPrice(dto.getPrice());
        transfer.setTransferDate(LocalDateTime.now());
        transferRepository.save(transfer);

        VehicleTransferResponseDto response = new VehicleTransferResponseDto();
        BeanUtils.copyProperties(transfer, response);
        response.setVehicleId(vehicle.getId());
        response.setPreviousOwnerName(previousOwner.getName());
        response.setNewOwnerName(newOwner.getName());
        response.setOldPlateNumber(oldPlate != null ? oldPlate.getPlateNumber() : null);
        response.setNewPlateNumber(newPlate.getPlateNumber());

        return ApiResponse.success("Vehicle transferred successfully", HttpStatus.OK, response);
    }
}