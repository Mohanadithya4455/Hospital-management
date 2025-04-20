package com.hospital.doctor.DoctorMapper;

import com.hospital.doctor.dto.DoctorResponseDto;
import com.hospital.doctor.dto.ScheduleRequestDto;
import com.hospital.doctor.dto.ScheduleResponseDto;
import com.hospital.doctor.entity.DoctorEntity;
import com.hospital.doctor.entity.ScheduleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ScheduleEntity toEntity(ScheduleRequestDto request){
        return modelMapper.map(request, ScheduleEntity.class);
    }

    public ScheduleResponseDto toResponse(DoctorEntity entity){
        return modelMapper.map(entity,ScheduleResponseDto.class);
    }
}
