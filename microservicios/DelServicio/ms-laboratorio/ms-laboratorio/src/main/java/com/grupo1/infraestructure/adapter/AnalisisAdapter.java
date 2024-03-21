package com.grupo1.infraestructure.adapter;



import com.grupo1.domain.aggregates.constants.Constants;
import com.grupo1.domain.aggregates.dto.AnalisisClinicoDTO;
import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestResultado;
import com.grupo1.domain.aggregates.response.MsExternalToHInsuranceRimacResponse;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.AnalisisServiceOut;
import com.grupo1.infraestructure.client.ToMSExternalApi;
import com.grupo1.infraestructure.entity.AnalisisClinicoEntity;
import com.grupo1.infraestructure.entity.ConsultaMedicaEntity;
import com.grupo1.infraestructure.entity.NombreAnalisisEntity;
import com.grupo1.infraestructure.mapper.GenericMapper;
import com.grupo1.infraestructure.repository.AnalisisRepository;
import com.grupo1.infraestructure.repository.ConsultaMedicaRepository;
import com.grupo1.infraestructure.repository.NombreAnalisisRepository;
import com.grupo1.infraestructure.util.CurrentTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalisisAdapter implements AnalisisServiceOut {

    private final AnalisisRepository analisisRepository;
    private final NombreAnalisisRepository nombreAnalisisRepository;
    private final ConsultaMedicaRepository consultaMedicaRepository;
    private final GenericMapper genericMapper;
    private final ToMSExternalApi toMSExternalApi;

    @Override
    public ResponseBase BuscarAnalisisEntityOut(Long id) {
        Optional<AnalisisClinicoEntity> analisisEntity= analisisRepository.findById(id);
        if(analisisEntity.isPresent()){
            return new ResponseBase(200,
                    "Registro encontrado con exito", analisisEntity);
        }
        return  new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase BuscarAnalisisDtoOut(Long id) {
       Optional<AnalisisClinicoEntity> analisisEntity= analisisRepository.findById(id);
       if(analisisEntity.isPresent()){
           AnalisisClinicoDTO analisisDto = genericMapper.
                   mapAnalisisClinicoEntityToAnalisiClinicoDTO(analisisEntity.get());

           analisisDto.setNombreDoctor(analisisEntity.get().getConsulta().getDoctor().getNombre()
           + " " + analisisEntity.get().getConsulta().getDoctor().getApellido());
           analisisDto.setNombrePaciente(analisisEntity.get().getConsulta().getPaciente().getNombre()
           + " " +analisisEntity.get().getConsulta().getPaciente().getApellido());

           return new ResponseBase(200, "Registro encontrado con exito",  analisisDto);
       }
       return new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase BuscarAllEnableAnalisisDtoOut() {
        List<AnalisisClinicoEntity> listaEntitiy = analisisRepository.findByEstado(true);
        Set<AnalisisClinicoDTO> listaDto = new HashSet<>();
        for(AnalisisClinicoEntity analisis : listaEntitiy) {
            AnalisisClinicoDTO analisiDto = genericMapper.mapAnalisisClinicoEntityToAnalisiClinicoDTO(analisis);
            listaDto.add(analisiDto);
        }
        return new ResponseBase(200, "Solicitud exitosa", listaDto);
    }

    @Override
    public ResponseBase RegisterAnalisisOut(RequestRegister requestRegister, String username) {
        ///////// Validar si la consulta existe ///////////
        Optional<ConsultaMedicaEntity> getConsulta = consultaMedicaRepository.findById(requestRegister.getIdConsulta());
        if(getConsulta.isEmpty())
            return new ResponseBase (404,
                    "No se encontró el registro de consulta", null);
        //////////////////////////////////////////////////////////
        ///////// Validar si nombre de analisis existe ///////////
        Optional<NombreAnalisisEntity> getNombreAnalisis = nombreAnalisisRepository
                .findByAnalisis(requestRegister.getNombreAnalisis());
        if(getNombreAnalisis.isEmpty())
            return new ResponseBase (404,
                    "No se encontró el nombre de analisis", null);
        //////////////////////////////////////////////////////////
         ///////Obtenemos la cobertura de seguro////////////
        String numDoc = getConsulta.get().getPaciente().getNumDocumento();
        String complejidad = getNombreAnalisis.get().getComplejidad();
        MsExternalToHInsuranceRimacResponse getCobertura = toMSExternalApi.
                getInfoExtRimac(numDoc, complejidad);


        // Creando la entiedad con Patron de diseño creacional Builder.
        AnalisisClinicoEntity analisis = AnalisisClinicoEntity.builder()
                .nombreSeguro(getCobertura.getNomAseguradora())
                .coberturaSeguro(getCobertura.getCobertura())
                .nombreAnalisis(getNombreAnalisis.get())
                .consulta(getConsulta.get())
                .usuarioCreacion(username)
                .fechaCreacion(CurrentTime.getTimestamp())
                .estado(Constants.STATUS_ACTIVE)
                .build();
        AnalisisClinicoEntity saveAnalisis = analisisRepository.save(analisis);

        return new ResponseBase(201, "Analisis Clinico registrado con exito",
                saveAnalisis);

    }

    @Override
    public ResponseBase UpdateTomaMuestraAnalisisOut(Long id, String username) {
        Optional<AnalisisClinicoEntity> getAnalisis = analisisRepository.findById(id);
        if(getAnalisis.isPresent()){
            getAnalisis.get().setFechaRecepcion(CurrentTime.getTimestamp());
            getAnalisis.get().setUsuarioRecepcion(username);
            getAnalisis.get().setUsuarioModificacion(username);
            getAnalisis.get().setFechaModificacion(CurrentTime.getTimestamp());

            AnalisisClinicoEntity saveAnalisis = analisisRepository.save(getAnalisis.get());
            return new ResponseBase(200, "Toma de muestra exitosa", saveAnalisis);
        }

        return new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase UpdateResultadoAnalisisOut(RequestResultado requestResultado, String username) {
        Optional<AnalisisClinicoEntity> getAnalisis = analisisRepository.findById(requestResultado.getIdAnalisis());
        if(getAnalisis.isPresent()){
            getAnalisis.get().setResultado(requestResultado.getResultado());
            getAnalisis.get().setUsuarioModificacion(username);
            getAnalisis.get().setFechaModificacion(CurrentTime.getTimestamp());

            AnalisisClinicoEntity saveAnalisis = analisisRepository.save(getAnalisis.get());
            return new ResponseBase(200, "Registro de resultado exitoso.", saveAnalisis);
        }

        return new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase DeleteAnalisisOut(Long id, String username) {
            Optional<AnalisisClinicoEntity> getAnalisis = analisisRepository.findById(id);
            if(getAnalisis.isPresent()){
                getAnalisis.get().setEstado(Constants.STATUS_INACTIVE);
                getAnalisis.get().setFechaEliminacion(CurrentTime.getTimestamp());
                getAnalisis.get().setUsuarioEliminacion(username);

                AnalisisClinicoEntity saveAnalisis = analisisRepository.save(getAnalisis.get());

                return new ResponseBase(200, "Registro borrado con exito", saveAnalisis);

            }
        return new ResponseBase(404, "Registro no encontrado", null);
    }
}
