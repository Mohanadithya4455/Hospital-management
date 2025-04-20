package com.hospital.doctor.controller;

import com.hospital.doctor.AuthService;
import com.hospital.doctor.dto.*;
import com.hospital.doctor.exceptions.NotAllowedException;
import com.hospital.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctors/api")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    @Autowired
    private AuthService authService;

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequestDto request ,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        AuthDto user = authService.getUserDetails(jwt);

       DoctorResponseDto response = doctorService.createDoctor(request,user.getEmail(),user.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDto request, @RequestHeader("Authorization") String jwt) throws Exception {
        AuthDto auth = authService.getUserDetails(jwt);

        DoctorResponseDto response = doctorService.updateDoctor(id, request, auth.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> deleteUser(@PathVariable Long id,@RequestHeader("Authorization") String jwt) {
        AuthDto auth = authService.getUserDetails(jwt);

        doctorService.deleteDoctor(id,auth.getId(),auth.getRole());
        return  new ResponseEntity("Deleted Successfully",HttpStatus.OK);
    }

    @PutMapping("/doctor/{id}/set-availability")
    public ResponseEntity<String> setAvailability(
            @PathVariable Long id,
            @RequestBody DoctorAvailabilityRequest request, @RequestHeader("Authorization") String jwt) throws NotAllowedException {
        AuthDto auth = authService.getUserDetails(jwt);
        doctorService.setDoctorAvailability(id, request, auth.getId());
        return ResponseEntity.ok("Availability updated successfully.");
    }

    @GetMapping("/doctor/{id}/{date}/booked-slots")
    public ResponseEntity<List<BookedSlotResponse>> getBookedSlots(
            @PathVariable Long id,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestHeader("Authorization") String jwt) throws Exception {
        AuthDto auth = authService.getUserDetails(jwt);
        List<BookedSlotResponse> bookedSlots = doctorService.getBookedSlotTimes(id, date, auth.getId());
        return ResponseEntity.ok(bookedSlots);
    }


}