package org.example.Mappers;

import org.example.DTO.ConsultaionsDTO;
import org.example.DTO.SchedulesDTO;
import org.example.Models.CONSULTATIONS;
import org.example.Models.SCHEDULES;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface SchedulesMapper {

    SchedulesMapper INSTANCE = Mappers.getMapper(SchedulesMapper.class);
    SchedulesDTO toSchedulesDto (SCHEDULES s);

    SCHEDULES toModel(SchedulesDTO dto);



}
