package org.example.Mappers;

import org.example.DAO.DoctorsDAO;
import org.example.DTO.DoctorsDTO;
import org.example.Models.DOCTORS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DoctorsMapper {

    DoctorsMapper INSTANCE = Mappers.getMapper(DoctorsMapper.class);

    DoctorsDTO todoctorDto (DOCTORS d);

    DOCTORS toModel(DoctorsDTO dto);


}
