package com.hospital.doctor.dto;

import com.hospital.doctor.enums.Day;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorRequestDto {
    //@NotBlank(message = "Name is required")
    private String name;

    //@NotBlank(message = "Qualifications are required")
    private String qualifications;

//    @NotBlank(message = "Registration number is required")
//    @Pattern(regexp = "^[A-Z0-9]{5,20}$")
    private String registrationNumber;

    //@NotBlank(message = "Specialization is required")
    private String specialization;

    //@NotBlank(message="languages is required")
    private String languages;

//    @NotNull(message = "Experience years are required")
//    @Min(0)
    private Integer experienceYears;

   // @NotBlank(message = "Location is required")
    private String location;
    //@NotBlank(message = "profile image is required")
    private String profileImage;
   // @NotBlank(message = "field Availablefrom is required")
    private LocalTime availableFrom ;
//
    // @NotBlank(message = "field avialableTo is required")
    private LocalTime availableTo;
    //@NotBlank(message = "field avaialable is required")
    private LocalDate availableDate;
   // @NotBlank(message = "day is required")
    //@Enumerated(EnumType.STRING)
    private Day day;
//
    @Builder.Default
    private Set<BookedSlotResponse> bookedSlotEntities =new LinkedHashSet<>();

    @Builder.Default
    private List<ScheduleRequestDto> schedules=new ArrayList<>();
}