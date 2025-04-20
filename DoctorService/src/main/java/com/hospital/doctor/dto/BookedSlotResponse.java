package com.hospital.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedSlotResponse {
    private LocalDate slotDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;
}
