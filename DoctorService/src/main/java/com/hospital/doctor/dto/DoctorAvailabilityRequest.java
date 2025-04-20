package com.hospital.doctor.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityRequest {
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private LocalDate availableDate;
}
