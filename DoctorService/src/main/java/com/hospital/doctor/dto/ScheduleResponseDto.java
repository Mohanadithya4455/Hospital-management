package com.hospital.doctor.dto;




import com.hospital.doctor.enums.PatientType;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleResponseDto {
    String day;
    String time;
    PatientType patientType;
}
