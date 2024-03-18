package com.grupo1.infraestructure.adapter;

import com.grupo1.domain.aggregates.constants.Constants;
import com.grupo1.domain.aggregates.dto.NombreAnalisisDTO;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.NombreAnalisisServiceOut;
import com.grupo1.infraestructure.entity.NombreAnalisisEntity;
import com.grupo1.infraestructure.mapper.GenericMapper;
import com.grupo1.infraestructure.repository.NombreAnalisisRepository;
import com.grupo1.infraestructure.util.CurrentTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NombreAnalisisAdapter implements NombreAnalisisServiceOut {

    private final NombreAnalisisRepository nombreAnalisisRepository;
    private final GenericMapper genericMapper;


    @Override
    public ResponseBase findNombreAnalisisOut(String nombreAnalisis) {

        Optional<NombreAnalisisEntity> getAnalisis = nombreAnalisisRepository.findByAnalisis(nombreAnalisis);
        if (getAnalisis .isPresent()) {
            return new ResponseBase(302, "Informacion encotrada con exito", getAnalisis );
        }
        return new ResponseBase(404, "No se encontro la informacion", null);
    }

        @Override
    public ResponseBase findAllEnableNombreAnalisisOut() {
            List<NombreAnalisisDTO> analisisDTOList = new ArrayList<>();
            List<NombreAnalisisEntity> analisisEntities = nombreAnalisisRepository.findByEstado(true);
            for(NombreAnalisisEntity analisis : analisisEntities){
                NombreAnalisisDTO analisisDTO = genericMapper.mapNombreAnalisisEntityToNombreAnalisisDTO(analisis);
                analisisDTOList.add(analisisDTO);
            }
            return  new ResponseBase(302, "Informacion encotrada con exito", analisisDTOList);
    }

    @Override
    public ResponseBase registerNombreAnalisisOut(NombreAnalisisDTO nombreAnalisisDTO, String username) {

        if((nombreAnalisisDTO.getAnalisis()==null || nombreAnalisisDTO.getAnalisis().isEmpty()) ||
                !(nombreAnalisisDTO.getComplejidad().equals("LAB1") ||
                        nombreAnalisisDTO.getComplejidad().equals("LAB2") ||
                        nombreAnalisisDTO.getComplejidad().equals("LAB3"))) {
            return new ResponseBase(406, "Error en la informacion", null);
        }
        Optional<NombreAnalisisEntity> getAnalisis = nombreAnalisisRepository.
                findByAnalisis(nombreAnalisisDTO.getAnalisis());
        if (getAnalisis.isPresent()) {
            return new ResponseBase(406, "Nombre de analisis ya existe", null);
        }
        NombreAnalisisEntity nombreAnalisis = new NombreAnalisisEntity();
        nombreAnalisis.setAnalisis(nombreAnalisisDTO.getAnalisis());
        nombreAnalisis.setComplejidad(nombreAnalisisDTO.getComplejidad());
        nombreAnalisis.setUsuarioCreacion(username);
        nombreAnalisis.setFechaCreacion(CurrentTime.getTimestamp());
        nombreAnalisis.setEstado(Constants.STATUS_ACTIVE);

        nombreAnalisis = nombreAnalisisRepository.save(nombreAnalisis);

        return new ResponseBase(201, "Registrado con exito.", nombreAnalisis);


    }

    @Override
    public ResponseBase updateNombreAnalisisOut(NombreAnalisisDTO nombreAnalisisDTO, String username) {
        Optional<NombreAnalisisEntity> findAnalisis = nombreAnalisisRepository.
                findByAnalisis(nombreAnalisisDTO.getAnalisis());
        if(findAnalisis.isPresent()){
            if(nombreAnalisisDTO.getComplejidad().equals("LAB1") || nombreAnalisisDTO.getComplejidad().equals("LAB2") ||
                    nombreAnalisisDTO.getComplejidad().equals("LAB3")){
                findAnalisis.get().setComplejidad(nombreAnalisisDTO.getComplejidad());
                findAnalisis.get().setUsuarioModificacion(username);
                findAnalisis.get().setFechaModificacion(CurrentTime.getTimestamp());


                return new ResponseBase(200, "Registro actualizado",
                       nombreAnalisisRepository.save(findAnalisis.get()));
            }


        }
        return new ResponseBase(404, "No se encontro la especialidad.", null);

    }





    @Override
    public ResponseBase deleteNombreAnalisisOut(Long id, String username) {
        return new ResponseBase(200, "Desde  NombreAnalisisAdapter/delete, " +
                "registrado por "+username, null);
    }
}
