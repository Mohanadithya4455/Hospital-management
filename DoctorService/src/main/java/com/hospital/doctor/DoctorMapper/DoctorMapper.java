package com.hospital.doctor.DoctorMapper;

import com.hospital.doctor.dto.DoctorRequestDto;
import com.hospital.doctor.dto.DoctorResponseDto;
import com.hospital.doctor.entity.DoctorEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    @Autowired
    private ModelMapper modelMapper;

    public DoctorEntity toEntity(DoctorRequestDto request){
        return modelMapper.map(request,DoctorEntity.class);
    }

    public DoctorResponseDto toResponse(DoctorEntity entity){
        return modelMapper.map(entity,DoctorResponseDto.class);
    }


}
