package com.grupo1.infraestructure.mapper;


import com.grupo1.domain.aggregates.dto.DirecccionDTO;
import com.grupo1.domain.aggregates.dto.DoctorDTO;
import com.grupo1.domain.aggregates.dto.EspecialidadMedicaDTO;
import com.grupo1.domain.aggregates.request.RequestDoctor;
import com.grupo1.infraestructure.entity.DirecccionEntity;
import com.grupo1.infraestructure.entity.DoctorEntity;
import com.grupo1.infraestructure.entity.EspecialidadMedicaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GenericMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public DoctorDTO mapDoctorEntityToDoctorDTO(DoctorEntity doctorEntity){
        return modelMapper.map(doctorEntity, DoctorDTO.class);
    }

    public DirecccionEntity mapDireccionDtoToDireccionEntity(DirecccionDTO direcccionDTO){
        return modelMapper.map(direcccionDTO, DirecccionEntity.class);
    }


    public EspecialidadMedicaDTO mapEspecialidadEntityToEspecialidadDTO(EspecialidadMedicaEntity especilidad){
        return modelMapper.map(especilidad, EspecialidadMedicaDTO.class);
    }

}
