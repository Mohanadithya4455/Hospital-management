package com.hospital.doctor.repository;
import com.hospital.doctor.dto.DoctorRequestDto;
import com.hospital.doctor.dto.DoctorResponseDto;
import com.hospital.doctor.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    List<DoctorEntity> findBySpecialization(String specialization);

    List<DoctorEntity> findByName(String name);

    DoctorEntity findByRegistrationNumber(String registrationNumber);

    DoctorEntity findByUserId(int userId);

}
