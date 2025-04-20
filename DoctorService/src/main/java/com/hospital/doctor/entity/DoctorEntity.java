package com.hospital.doctor.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hospital.doctor.enums.Day;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctors")
@EntityListeners(AuditingEntityListener.class)
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_image")
    private byte[] profileImage;

    @Column(name="Doctor_name")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String qualifications;
    private String email;
    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private String specialization;

    private String languages;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name="userId")
    private int userId;

    @Column
    private String location;


    @Column
//    @NotNull(message = "available from should not be null")
    private LocalTime availableFrom;

    @Column
    private LocalTime availableTo;

    @Column
    private LocalDate availableDate;


     @Enumerated(EnumType.STRING)
     @Column
     private Day day;



     @OneToMany(mappedBy = "doctorEntity", cascade = CascadeType.ALL)
        @JsonManagedReference
        @Builder.Default
        private Set<BookedSlotEntity> bookedSlotEntities =new LinkedHashSet<>();


    @OneToMany(mappedBy = "doctorEntitySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ScheduleEntity> schedules;


    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
