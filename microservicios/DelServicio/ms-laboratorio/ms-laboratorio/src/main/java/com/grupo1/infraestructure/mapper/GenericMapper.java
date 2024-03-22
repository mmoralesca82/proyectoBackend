package com.grupo1.infraestructure.mapper;


import com.grupo1.domain.aggregates.dto.AnalisisClinicoDTO;
import com.grupo1.domain.aggregates.dto.ConsultaMedicaDTO;
import com.grupo1.domain.aggregates.dto.NombreAnalisisDTO;
import com.grupo1.infraestructure.entity.AnalisisClinicoEntity;
import com.grupo1.infraestructure.entity.ConsultaMedicaEntity;
import com.grupo1.infraestructure.entity.NombreAnalisisEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GenericMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public NombreAnalisisDTO mapNombreAnalisisEntityToNombreAnalisisDTO(NombreAnalisisEntity analisis){
        return modelMapper.map(analisis, NombreAnalisisDTO.class);
    }

    public ConsultaMedicaDTO mapConsultaMedicaEntityToConsultaMedicaDTO(ConsultaMedicaEntity consulta){
        return modelMapper.map(consulta, ConsultaMedicaDTO.class);
    }

}
