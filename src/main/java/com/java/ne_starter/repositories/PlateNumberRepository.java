package com.java.ne_starter.repositories;

import com.java.ne_starter.models.PlateNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {
    List<PlateNumber> findByOwnerId(Long ownerId);
    PlateNumber findByPlateNumber(String plateNumber);
}