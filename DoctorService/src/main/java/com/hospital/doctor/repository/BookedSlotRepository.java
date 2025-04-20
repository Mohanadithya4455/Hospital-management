package com.hospital.doctor.repository;

import com.hospital.doctor.entity.BookedSlotEntity;
import com.hospital.doctor.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookedSlotRepository extends JpaRepository<BookedSlotEntity, Long> {
    List<BookedSlotEntity> findByDoctorEntityAndSlotDate(DoctorEntity doctorEntity, LocalDate slotDate);

}
