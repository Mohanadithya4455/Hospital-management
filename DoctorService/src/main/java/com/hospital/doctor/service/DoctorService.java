package com.hospital.doctor.service;

import com.hospital.doctor.dto.*;
import com.hospital.doctor.exceptions.NotAllowedException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface DoctorService {

    DoctorResponseDto createDoctor(DoctorRequestDto request, String email, int id) throws Exception;

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto getDoctorById(Long id);

    DoctorResponseDto updateDoctor(Long id, DoctorRequestDto request, int authId) throws Exception;

    void deleteDoctor(Long id, int authId, Role role);

    List<DoctorResponseDto> getDoctorsBySpecialization(String specialization);

    List<DoctorResponseDto> getDoctorsByName(String name);

    DoctorResponseDto getDoctorByRegistrationNumber(String registrationNumber);

    void setDoctorAvailability(Long doctorId, DoctorAvailabilityRequest request, int id) throws NotAllowedException;

    List<BookedSlotResponse> getBookedSlotTimes(Long id, LocalDate date, int authId) throws Exception;

    Map<LocalTime, String> getAvailableSlots(Long doctorId, LocalDate date);
}
