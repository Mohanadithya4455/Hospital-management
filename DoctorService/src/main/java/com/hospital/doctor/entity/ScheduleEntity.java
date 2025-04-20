package com.hospital.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hospital.doctor.enums.PatientType;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;


@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String day;

    @Column(name = "time_slot")
    private String time;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private DoctorEntity doctorEntitySchedule;


    @Enumerated(EnumType.STRING)
    @Column(name = "patient_type", nullable = false)
    private PatientType patientType;


}
