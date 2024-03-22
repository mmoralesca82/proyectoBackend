package com.grupo1.infraestructure.mapper;


import com.grupo1.domain.aggregates.dto.ProcedimientoDTO;
import com.grupo1.domain.aggregates.dto.TriajeDTO;
import com.grupo1.infraestructure.entity.ProcedimientoMedicoEntity;
import com.grupo1.infraestructure.entity.TriajeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GenericMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public TriajeDTO mapTriajeEntityToTriajeDTO(TriajeEntity triaje){
        return modelMapper.map(triaje, TriajeDTO.class);
    }

    public ProcedimientoDTO mapProcedimientoMedEntityToProcedimientoMedDTO(ProcedimientoMedicoEntity procedimiento){
        return modelMapper.map(procedimiento, ProcedimientoDTO.class);
    }
}
