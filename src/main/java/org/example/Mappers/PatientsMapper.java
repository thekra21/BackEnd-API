package org.example.Mappers;

import org.example.DTO.DoctorsDTO;
import org.example.DTO.PatientsDTO;
import org.example.Models.DOCTORS;
import org.example.Models.PATIENTS;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface PatientsMapper {

    PatientsMapper INSTANCE = Mappers.getMapper(PatientsMapper.class);

    PatientsDTO topatientDTO (PATIENTS p);

    PATIENTS toModel(PatientsDTO dto);

}
