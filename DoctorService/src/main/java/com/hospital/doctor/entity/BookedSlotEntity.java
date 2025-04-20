package com.hospital.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookedSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookedSlotId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorEntity;

    private LocalDate slotDate;

    @Column
    private LocalTime slotStartTime;

    @Column
    private LocalTime slotEndTime;



    @Transient
    @Builder.Default
    private boolean slotAvailable = true;
}
