package com.java.ne_starter.services.implementations;

import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.dtos.transfer.VehicleTransferResponseDto;
import com.java.ne_starter.exceptions.NotFoundException;
import com.java.ne_starter.models.PlateNumber;
import com.java.ne_starter.models.Vehicle;
import com.java.ne_starter.models.VehicleTransfer;
import com.java.ne_starter.repositories.PlateNumberRepository;
import com.java.ne_starter.repositories.VehicleRepository;
import com.java.ne_starter.repositories.VehicleTransferRepository;
import com.java.ne_starter.services.interfaces.VehicleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleHistoryServiceImpl implements VehicleHistoryService {

    private final VehicleTransferRepository transferRepository;
    private final VehicleRepository vehicleRepository;
    private final PlateNumberRepository plateNumberRepository;

    @Override
    public ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> getHistoryByChassis(String chassisNumber) {
        Vehicle vehicle = vehicleRepository.findByChassisNumber(chassisNumber)
                .orElseThrow(() -> new NotFoundException("Vehicle with chassis number not found"));

        List<VehicleTransfer> transfers = transferRepository.findByVehicleId(vehicle.getId());
        return buildTransferResponse(transfers, "Vehicle ownership history fetched by chassis");
    }

    @Override
    public ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> getHistoryByPlate(String plateNumber) {
        PlateNumber plate = plateNumberRepository.findByPlateNumber(plateNumber);
        if (plate == null) {
            throw new NotFoundException("Plate number not found");
        }

        List<VehicleTransfer> transfers = transferRepository.findByOldPlateIdOrNewPlateId(plate.getId(), plate.getId());
        if (transfers.isEmpty()) {
            throw new NotFoundException("No transfer records found for this plate");
        }

        return buildTransferResponse(transfers, "Vehicle ownership history fetched by plate number");
    }

    private ResponseEntity<ApiResponse<List<VehicleTransferResponseDto>>> buildTransferResponse(
            List<VehicleTransfer> transfers, String message) {

        List<VehicleTransferResponseDto> history = transfers.stream().map(transfer -> {
            VehicleTransferResponseDto dto = new VehicleTransferResponseDto();
            BeanUtils.copyProperties(transfer, dto);
            dto.setVehicleId(transfer.getVehicle().getId());
            dto.setPreviousOwnerName(transfer.getPreviousOwner().getName());
            dto.setNewOwnerName(transfer.getNewOwner().getName());
            dto.setOldPlateNumber(transfer.getOldPlate() != null ? transfer.getOldPlate().getPlateNumber() : null);
            dto.setNewPlateNumber(transfer.getNewPlate() != null ? transfer.getNewPlate().getPlateNumber() : null);
            return dto;
        }).collect(Collectors.toList());

        return ApiResponse.success(message, HttpStatus.OK, history);
    }
}
