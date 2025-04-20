package com.hospital.doctor.dto;
import com.hospital.doctor.enums.PatientType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDto {
    @NotBlank(message = "Day is required")
    String day;

    @NotBlank(message = "Time slot is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}-\\d{2}:\\d{2}$", message = "Invalid time format (HH:MM-HH:MM)")
    String time;

    @NotNull(message = "Patient type is required")
    PatientType patientType;
}