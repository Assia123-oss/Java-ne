package com.java.ne_starter.repositories;

import com.java.ne_starter.models.VehicleTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleTransferRepository extends JpaRepository<VehicleTransfer, Long> {
    List<VehicleTransfer> findByVehicleId(Long vehicleId);
    List<VehicleTransfer> findByOldPlateIdOrNewPlateId(Long oldPlateId, Long newPlateId);

}