package com.java.ne_starter.models;

import com.java.ne_starter.enumerations.PlateStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "plate_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateNumber extends Base {

    @Column(name = "plate_number", unique = true, nullable = false)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlateStatus status = PlateStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "issued_date", nullable = false)
    private LocalDate issuedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
