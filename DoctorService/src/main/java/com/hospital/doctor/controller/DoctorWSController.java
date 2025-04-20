package com.hospital.doctor.controller;

import com.hospital.doctor.dto.DoctorResponseDto;
import com.hospital.doctor.service.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctors")
public class DoctorWSController {
    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping("getbyid/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {

        DoctorResponseDto response = doctorService.getDoctorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getalldoctors")
    public ResponseEntity<?> getAllDoctors() {
        List<DoctorResponseDto> response = doctorService.getAllDoctors();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-specialization/{specialization}")
    public ResponseEntity<?> getBySpecialization(
            @PathVariable String specialization,
            Pageable pageable) {


        List<DoctorResponseDto> response = doctorService.getDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/by-name/{name}")
    public ResponseEntity<?> getByName(
            @PathVariable String name) {
        return new ResponseEntity<>(doctorService.getDoctorsByName(name),
                HttpStatus.OK);
    }
    @GetMapping("/doctor/{id}/{date}/available-slots")
    public ResponseEntity<Map<LocalTime, String>> getAvailableSlots(
            @PathVariable Long id,
            @PathVariable LocalDate date) {
        Map<LocalTime, String> availableSlots = doctorService.getAvailableSlots(id, date);
        return ResponseEntity.ok(availableSlots);
    }
    @GetMapping("/getbyregno/{regNo}")
    public ResponseEntity<DoctorResponseDto> getDoctorbaseonRegNo(String regNo) {
        DoctorResponseDto response= doctorService.getDoctorByRegistrationNumber(regNo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
