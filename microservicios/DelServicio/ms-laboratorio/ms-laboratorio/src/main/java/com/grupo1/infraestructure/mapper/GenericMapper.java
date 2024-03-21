package com.grupo1.infraestructure.mapper;


import com.grupo1.domain.aggregates.dto.AnalisisClinicoDTO;
import com.grupo1.infraestructure.entity.AnalisisClinicoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GenericMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public AnalisisClinicoDTO mapAnalisisClinicoEntityToAnalisiClinicoDTO(AnalisisClinicoEntity analisis){
        return modelMapper.map(analisis, AnalisisClinicoDTO.class);
    }

}
