package com.hospital.doctor.service;

import com.hospital.doctor.DoctorMapper.BookedSlotMapper;
import com.hospital.doctor.DoctorMapper.DoctorMapper;
import com.hospital.doctor.DoctorMapper.ScheduleMapper;
import com.hospital.doctor.dto.*;
import com.hospital.doctor.entity.BookedSlotEntity;
import com.hospital.doctor.entity.DoctorEntity;
import com.hospital.doctor.entity.ScheduleEntity;
import com.hospital.doctor.exceptions.NotAllowedException;
import com.hospital.doctor.exceptions.DoctorNameNotFound;
import com.hospital.doctor.exceptions.DoctorNotFoundException;
//import com.hospital.doctor.exceptions.InvalidDateException;
import com.hospital.doctor.repository.BookedSlotRepository;
import com.hospital.doctor.repository.DoctorRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    private final DoctorMapper doctorMapper;
    private final ScheduleMapper scheduleMapper;

    private final BookedSlotMapper bookedSlotMapper;


    private final DoctorRepository doctorRepository;
    private final BookedSlotRepository bookedSlotRepository;
    @Transactional
    @Override
    public DoctorResponseDto createDoctor(DoctorRequestDto request, String email, int id) throws Exception {

        DoctorEntity doc = doctorRepository.findByUserId(id);
        if(doc!=null){
            throw new Exception("Already user with this id and email");
        }
        DoctorEntity doctorEntity = doctorMapper.toEntity(request);
                      doctorEntity.setEmail(email);
                      doctorEntity.setUserId(id);
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            try {
                doctorEntity.setProfileImage(Base64.getDecoder().decode(request.getProfileImage()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }
        /* saving entity */

        if(doctorEntity.getSchedules() != null)
        {
            for(ScheduleEntity schedule :doctorEntity.getSchedules()){
                schedule.setDoctorEntitySchedule(doctorEntity);
            }
        }

        if (doctorEntity.getBookedSlotEntities() != null) {
            for (BookedSlotEntity slot : doctorEntity.getBookedSlotEntities()) {
                slot.setDoctorEntity(doctorEntity);  // Make sure the field name matches your entity
            }
        }

        DoctorEntity savedDoctorEntity = doctorRepository.save(doctorEntity);

        return doctorMapper.toResponse(savedDoctorEntity);
    }

    @Transactional
    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        List<DoctorResponseDto> result=  doctorRepository.findAll().stream().map(doctorMapper::toResponse).toList();
      if(result.isEmpty()){
          throw new DoctorNotFoundException("Doctors not found ask the admin to add the doctors");
      }
      return result;

    }


    //GET http://localhost:8080/getalldoctors?page=0&size=5&sort=name,asc


    @Transactional
    @Override
    public DoctorResponseDto getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toResponse)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with ID " + id + " not found"));
    }




    @Transactional
    @Override
    public void deleteDoctor(Long id, int authId, Role role) {

        DoctorEntity doc = doctorRepository.findById(id).get();

        if(doc!=null && doc.getUserId()==authId && doc.getId()==id && role==Role.ROLE_DOCTOR) {
            doctorRepository.deleteById(id);
        }

       else{
           throw new DoctorNotFoundException("Doctor with ID " + id + " not found or you are not allowed to delete  ");
        }
    }

    @Transactional
    @Override
    public List<DoctorResponseDto> getDoctorsBySpecialization(String specialization) {

        if(specialization==null || specialization.isEmpty()){
            throw new DoctorNameNotFound("Specialization is null or empty--enter the valid Sepcialization");
        }
        List<DoctorEntity> doctors = doctorRepository.findBySpecialization(specialization);

        if(doctors.isEmpty()){
            throw new DoctorNotFoundException("Doctor with specialization  "+specialization +" not found");
        }

      return doctors.stream().map(doctorMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<DoctorResponseDto> getDoctorsByName(String name) {
        try {
            List<DoctorResponseDto> response = doctorRepository
                    .findByName(name).stream()
                    .map(doctorMapper::toResponse).collect(Collectors.toList());


            if (response.isEmpty()) {
                throw new DoctorNameNotFound("Doctor with name '" + name + "' not found");
            }

            return response;
        } catch (Exception e) {
            throw new DoctorNameNotFound("Doctor with name '" + name + "' not found");
        }
    }

@Transactional
    @Override
    public DoctorResponseDto getDoctorByRegistrationNumber(String registrationNumber) {
        if(registrationNumber==null || registrationNumber.isEmpty()){
            throw new DoctorNotFoundException("Doctor with registration number " + registrationNumber + " not found");
        }
        if(doctorRepository.findByRegistrationNumber(registrationNumber)==null){
            throw new DoctorNotFoundException("Doctor with registration number " + registrationNumber + " not found");
        }

        DoctorEntity entity = doctorRepository.findByRegistrationNumber(registrationNumber);

        return  doctorMapper.toResponse(entity);
    }
    @Transactional
    @Override
    public Map<LocalTime, String> getAvailableSlots(Long doctorId, LocalDate date) {
        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalTime from = doctor.getAvailableFrom();
        LocalTime to = doctor.getAvailableTo();

        List<BookedSlotEntity> bookedSlotEntities = bookedSlotRepository
                .findByDoctorEntityAndSlotDate(doctor, date);

        Set<LocalTime> bookedTimes = bookedSlotEntities.stream()
                .map(BookedSlotEntity::getSlotStartTime)
                .collect(Collectors.toSet());

        Map<LocalTime, String> slots = new LinkedHashMap<>();
        LocalTime current = from;

        while (current.isBefore(to)) {
            String status = bookedTimes.contains(current) ? "Not Available" : "Available";
            slots.put(current, status);
            current = current.plusMinutes(15);
        }

        return slots;
    }

    @Transactional
    @Override
    public void setDoctorAvailability(Long doctorId, DoctorAvailabilityRequest request, int id) throws NotAllowedException {

        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        if(id != doctor.getUserId()){
            throw new NotAllowedException("You are not allowed to change the status of others");
        }
        doctor.setAvailableFrom(request.getAvailableFrom());
        doctor.setAvailableTo(request.getAvailableTo());
        doctor.setAvailableDate(request.getAvailableDate());

        doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public List<BookedSlotResponse> getBookedSlotTimes(Long doctorId, LocalDate date, int authId) throws Exception {
        if(date==null)
        {
            throw new Exception("Enter date!");
        }

        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        if(doctor.getUserId()!=authId){
            throw new NotAllowedException("You are not allowed to get the deatils of others");
        }
        List<BookedSlotEntity> slots = bookedSlotRepository.findByDoctorEntityAndSlotDate(doctor, date);

        return slots.stream().map(bookedSlotMapper::toResponse).collect(Collectors.toList());
    }


@Transactional
@Override
    public DoctorResponseDto updateDoctor(Long id, DoctorRequestDto request, int authId) throws Exception {


        DoctorEntity existing  = doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor with ID " + id + " not found"));
        if(existing.getUserId()!=authId){
            throw new NotAllowedException("You are not able to update the others details");
        }
        existing.setName(request.getName());
        existing.setQualifications(request.getQualifications());
        existing.setRegistrationNumber(request.getRegistrationNumber());
        existing.setSpecialization(request.getSpecialization());
        existing.setLanguages(request.getLanguages());
        existing.setExperienceYears(request.getExperienceYears());
        existing.setLocation(request.getLocation());
        existing.setAvailableFrom(request.getAvailableFrom());
        existing.setAvailableDate(request.getAvailableDate());
        existing.setAvailableTo(request.getAvailableTo());
        existing.setDay(request.getDay());

        // Only decode and update image if it's provided
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            try {
                existing.setProfileImage(Base64.getDecoder().decode(request.getProfileImage()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }


        // Update schedules (optional: replace all or merge)
        if (request.getSchedules() != null && !request.getSchedules().isEmpty()) {
            existing.getSchedules().clear();  // Clear existing if replacing
            existing.getSchedules().addAll(
                    request.getSchedules().stream().map(s -> ScheduleEntity.builder()
                            .day(s.getDay())
                            .time(s.getTime())
                            .patientType(s.getPatientType())
                            .build()).toList()
            );
        }
        Set<BookedSlotEntity> slots = request.getBookedSlotEntities().stream()
                .map(slot -> BookedSlotEntity.builder()
                        .slotDate(slot.getSlotDate())
                        .slotStartTime(slot.getSlotStartTime())
                        .slotEndTime(slot.getSlotEndTime())
                        .build()
                ).collect(Collectors.toSet());

        slots.forEach(slot -> slot.setDoctorEntity(existing));
        existing.getBookedSlotEntities().clear();
        existing.getBookedSlotEntities().addAll(slots);

       doctorRepository.save(existing);
        return doctorMapper.toResponse(existing);
    }

}
