package com.hospital.doctor.DoctorMapper;

import com.hospital.doctor.dto.BookedSlotRequest;
import com.hospital.doctor.dto.BookedSlotResponse;
import com.hospital.doctor.dto.ScheduleRequestDto;
import com.hospital.doctor.dto.ScheduleResponseDto;
import com.hospital.doctor.entity.BookedSlotEntity;
import com.hospital.doctor.entity.DoctorEntity;
import com.hospital.doctor.entity.ScheduleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookedSlotMapper {

    @Autowired
    private ModelMapper modelMapper;

    public BookedSlotEntity toEntity(BookedSlotRequest request){
        return modelMapper.map(request, BookedSlotEntity.class);
    }

    public BookedSlotResponse toResponse(BookedSlotEntity entity){
        return modelMapper.map(entity,BookedSlotResponse.class);
    }
}
