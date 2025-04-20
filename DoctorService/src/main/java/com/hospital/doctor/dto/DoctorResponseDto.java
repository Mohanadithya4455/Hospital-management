package com.hospital.doctor.dto;


import com.hospital.doctor.entity.BookedSlotEntity;
import com.hospital.doctor.enums.Day;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String qualifications;
    private String registrationNumber;
    private String specialization;
    private String languages;
    private Integer experienceYears;
    private String location;
    private String profileImage;
    private LocalTime availableFrom ;
    private LocalTime availableTo;
    private LocalDate availableDate;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Builder.Default
    private Set<BookedSlotResponse> bookedSlotEntities =new LinkedHashSet<>();

    @Builder.Default
    private List<ScheduleResponseDto> schedules=new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
}
