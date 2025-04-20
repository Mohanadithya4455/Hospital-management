package com.hospital.doctor.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hospital.doctor.entity.DoctorEntity;
import com.hospital.doctor.entity.ScheduleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookedSlotRequest {
    private LocalDate slotDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;

}


