package com.grupo1.infraestructure.adapter;


import com.grupo1.domain.aggregates.constants.Constants;
import com.grupo1.domain.aggregates.dto.TriajeDTO;
import com.grupo1.domain.aggregates.request.RequestTriaje;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.TriajeServiceOut;
import com.grupo1.infraestructure.entity.ConsultaMedicaEntity;
import com.grupo1.infraestructure.entity.TriajeEntity;
import com.grupo1.infraestructure.mapper.GenericMapper;
import com.grupo1.infraestructure.repository.ConsultaMedicaRepository;
import com.grupo1.infraestructure.repository.TriajeRepository;
import com.grupo1.infraestructure.util.CurrentTime;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Builder
public class TriajeAdapter implements TriajeServiceOut {

    private final TriajeRepository triajeRepository;
    private final ConsultaMedicaRepository consultaMedicaRepository;
    private final GenericMapper genericMapper;

    @Override
    public ResponseBase BuscarTriajeEntityOut(Long id) {
        Optional<TriajeEntity> triajeEntity= triajeRepository.findById(id);
        if(triajeEntity.isPresent()){
            return new ResponseBase(200,
                    "Registro encontrado con exito", triajeEntity);
        }
        return  new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase BuscarTriajeDtoOut(Long id) {
        Optional<TriajeEntity> triajeEntity = triajeRepository.findById(id);

        if(triajeEntity.isPresent()){
            TriajeDTO triajeDTO = genericMapper.mapTriajeEntityToTriajeDTO(triajeEntity.get());
            return new ResponseBase(200, "Registro encontrado con exito",  triajeDTO);
        }
        return new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase BuscarAllEnableTriajeDtoOut() {
        List<TriajeEntity> listaEntitiy = triajeRepository.findByEstado(true);
        Set<TriajeDTO> listaDto = new HashSet<>();
        for(TriajeEntity triajeEntity : listaEntitiy) {
            TriajeDTO triajeDTO = genericMapper.mapTriajeEntityToTriajeDTO(triajeEntity);
            listaDto.add(triajeDTO);
        }
        return new ResponseBase(200, "Solicitud exitosa", listaDto);
    }

    @Override
    public ResponseBase UpdateTriajeOut(RequestTriaje requestTriaje, String username) {
        Optional<ConsultaMedicaEntity> getCosulta = consultaMedicaRepository.findById(requestTriaje.getIdConsulta());
        if(getCosulta.isPresent() && getCosulta.get().getEstado()){
            Optional<TriajeEntity> getTriaje = triajeRepository.findById(getCosulta.get().getTriaje().getIdTriaje());
            getTriaje.get().setFechaTriaje(CurrentTime.getTimestamp());
            getTriaje.get().setTempCorporal(requestTriaje.getTempCorporal());
            getTriaje.get().setPresionArterial(requestTriaje.getPresionArterial());
            getTriaje.get().setSatOxigeno(requestTriaje.getSatOxigeno());
            getTriaje.get().setSintoma(requestTriaje.getSintoma());
            getTriaje.get().setObservaciones(requestTriaje.getObservaciones());
            getTriaje.get().setFechaModificacion(CurrentTime.getTimestamp());
            getTriaje.get().setUsuarioModificacion(username);
            getTriaje.get().setEstado(Constants.STATUS_ACTIVE);

            TriajeEntity saveTriaje = triajeRepository.save(getTriaje.get());

            return  new ResponseBase(200, "Registro actualizado", saveTriaje);
        }
        return new ResponseBase(404, "Consulta no encontrada", null);
    }

    @Override
    public ResponseBase DeleteTriajeOut(Long id, String username) {
        Optional<TriajeEntity> getTriaje = triajeRepository.findById(id);
        if(getTriaje.isPresent()){
            getTriaje.get().setFechaEliminacion(CurrentTime.getTimestamp());
            getTriaje.get().setUsuarioEliminacion(username);
            getTriaje.get().setEstado(Constants.STATUS_INACTIVE);

            TriajeEntity saveTriaje = triajeRepository.save(getTriaje.get());

            return  new ResponseBase(200, "Registro eliminado", saveTriaje);
        }
        return new ResponseBase(404, "Registro no encontrado", null);
    }
}
