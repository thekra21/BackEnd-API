package org.example.Mappers;

import org.example.DTO.MedicalReportsDTO;
import org.example.DTO.PatientsDTO;
import org.example.Models.MedicalReports;
import org.example.Models.PATIENTS;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalReportsMapper {

    MedicalReportsMapper INSTANCE = Mappers.getMapper(MedicalReportsMapper.class);

    MedicalReportsDTO toMedicalDTO (MedicalReports m);

    MedicalReports toModel(MedicalReportsDTO dto);


}
