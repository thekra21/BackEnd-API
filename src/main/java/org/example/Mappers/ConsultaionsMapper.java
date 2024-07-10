package org.example.Mappers;

import org.example.DTO.ConsultaionsDTO;
import org.example.DTO.DoctorsDTO;
import org.example.Models.CONSULTATIONS;
import org.example.Models.DOCTORS;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConsultaionsMapper {
    ConsultaionsMapper INSTANCE = Mappers.getMapper(ConsultaionsMapper.class);

    ConsultaionsDTO toConsultaionsDto (CONSULTATIONS c);

    CONSULTATIONS toModel(ConsultaionsDTO dto);


}
