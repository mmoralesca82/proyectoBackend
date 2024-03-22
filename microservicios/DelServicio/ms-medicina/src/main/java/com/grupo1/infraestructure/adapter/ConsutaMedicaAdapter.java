package com.grupo1.infraestructure.adapter;


import com.grupo1.domain.aggregates.constants.Constants;
import com.grupo1.domain.aggregates.dto.ConsultaMedicaDTO;
import com.grupo1.domain.aggregates.dto.ProcedimientoDTO;
import com.grupo1.domain.aggregates.dto.TriajeDTO;
import com.grupo1.domain.aggregates.request.RequestProcedimiento;
import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestAtencionMedica;
import com.grupo1.domain.aggregates.response.MsExternalToHInsuranceRimacResponse;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.ConsultaMedicaServiceOut;
import com.grupo1.infraestructure.client.ToMSExternalApi;
import com.grupo1.infraestructure.entity.*;
import com.grupo1.infraestructure.mapper.GenericMapper;
import com.grupo1.infraestructure.repository.*;
import com.grupo1.infraestructure.util.CurrentTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ConsutaMedicaAdapter implements ConsultaMedicaServiceOut {

    private final ConsultaMedicaRepository consultaMedicaRepository;
    private final ProcedimientoMedicoRepository procedimientoMedicoRepository;
    private  final DoctorRepository doctorRepository;
    private final PacienteRepository pacienteRepository;
    private final TriajeRepository triajeRepository;
    private final GenericMapper genericMapper;
    private final ToMSExternalApi toMSExternalApi;

    @Override
    public ResponseBase BuscarConsultaMedicaEntityOut(Long id) {
        Optional<ConsultaMedicaEntity> consultaEntity= consultaMedicaRepository.findById(id);
        if(consultaEntity.isPresent()){
            return new ResponseBase(200,
                    "Registro encontrado con exito", consultaEntity);
        }
        return  new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase BuscarAllProcedimientoMedicoEntityOut(Long idConsulta) {
        Optional<ConsultaMedicaEntity> getConsulta = consultaMedicaRepository.findById(idConsulta);
        if(getConsulta.isPresent()) {
            List<ProcedimientoMedicoEntity> listaEntities = procedimientoMedicoRepository
                    .findByConsulta(getConsulta.get());

            return new ResponseBase(200,
                    "Lista de procedimientos de la consulta "+idConsulta, listaEntities);
        }
        return  new ResponseBase(404, "No se encontraron procedimientos para la consulta "+idConsulta, null);
    }


    @Override
    public ResponseBase BuscarConsultaMedicaDtoOut(Long id) {
        Optional<ConsultaMedicaEntity> consultaEntity = consultaMedicaRepository.findById(id);
        if(consultaEntity.isPresent()){

            ConsultaMedicaDTO consultaDto = BuildConsultaMedicaDTO(consultaEntity.get());

            return new ResponseBase(200, "Registro encontrado con exito",  consultaDto);
        }
        return new ResponseBase(404, "Registro no encontrado", null);
    }

    @Override
    public ResponseBase BuscarAllEnableConsultaMedicaDtoOut() {
        List<ConsultaMedicaEntity> listaEntitiy = consultaMedicaRepository.findByEstado(true);
        Set<ConsultaMedicaDTO> listaDto = new HashSet<>();
        for(ConsultaMedicaEntity consultaEntity : listaEntitiy) {

            ConsultaMedicaDTO consultaDto = BuildConsultaMedicaDTO(consultaEntity);

            listaDto.add(consultaDto);
        }
        return new ResponseBase(200, "Solicitud exitosa", listaDto);
    }

    @Override
    public ResponseBase RegisterConsultaMedicaOut(RequestRegister requestRegister, String username) {
        //Al registrar la consulta se registra la entidad de triaje
        //////////////////Validamos el formato de la fecha////////////////////////
        Date date = new Date();
        try {
            DateFormat formateador = new SimpleDateFormat("dd/M/yy HH:mm");
            date = formateador.parse(requestRegister.getFechaConsulta());
        } catch (Exception e) {
            return new ResponseBase(406,
                    "Formato de fecha incorrecto. Ingresar formato dd/MM/yyyy HH:mm.", null);
        }
        //////////////////////////////////////////////////////////////////////////
        //////////////////Validamos que doctor exista en DB///////////////////////
        Optional<DoctorEntity> getDoctor = doctorRepository.findById(requestRegister.getIdDoctor());
        if(getDoctor.isEmpty() || !getDoctor.get().getEstado())
            return new ResponseBase(406,
                    "No se encuentra el doctor.", null);
        //////////////////////////////////////////////////////////////////////////
        //////////////////Validamos que paciente exista en DB//////////////////////
        Optional<PacienteEntity> getPaciente = pacienteRepository.findById(requestRegister.getIdPaciente());
        if(getPaciente.isEmpty() || !getPaciente.get().getEstado())
            return new ResponseBase(406,
                    "No se encuentra al paciente.", null);
        //////////////////////////////////////////////////////////////////////////
        //////////////////Verificamos cobertura de seguro/////////////////////////

        MsExternalToHInsuranceRimacResponse getCobertura = toMSExternalApi
                .getInfoExtRimac(getPaciente.get().getNumDocumento(),
                        getDoctor.get().getEspecialidadMedica().getComplejidad());
        if(getCobertura.getCobertura()==0.0){
            getCobertura.setNomAseguradora(" ");
        }

        //////////////////////////////////////////////////////////////////////////

        //Creamos el registro de triaje en DB

        TriajeEntity triaje = TriajeEntity.builder()
                .fechaCreacion(CurrentTime.getTimestamp())
                .usuarioCreacion(username)
                .estado(Constants.STATUS_ACTIVE)
                .build();

        TriajeEntity saveTriaje = triajeRepository.save(triaje);

        ConsultaMedicaEntity consulta = ConsultaMedicaEntity.builder()
                .fechaConsulta(date)
                .nombreSeguro(getCobertura.getNomAseguradora())
                .coberturaSeguro(getCobertura.getCobertura())
                .fechaCreacion(CurrentTime.getTimestamp())
                .usuarioCreacion(username)
                .estado(Constants.STATUS_ACTIVE)
                .paciente(getPaciente.get())
                .doctor(getDoctor.get())
                .triaje(saveTriaje)
                .build();
        ConsultaMedicaEntity saveConsulta = consultaMedicaRepository.save(consulta);

        return new ResponseBase(201, "Registro creado con exito.", saveConsulta);
    }

    @Override
    public ResponseBase UpdateAtencionConsultaMedicaOut(RequestAtencionMedica requestAtencionMedica, String username) {
        /////Verificamos si id consulta existe en DB/////////////
        Optional<ConsultaMedicaEntity> consultaEntity = consultaMedicaRepository.findById(requestAtencionMedica.getIdConsulta());
        if(consultaEntity.isEmpty() || !consultaEntity.get().getEstado())
            return new ResponseBase(404, "No se encontro el id de consulta.", null);
        if(consultaEntity.get().getTriaje().getFechaTriaje()==null)
            return new ResponseBase(406, "Paciente no ha pasado por triaje.", null);
        consultaEntity.get().setSintomas(requestAtencionMedica.getSintomas());
        consultaEntity.get().setDiagnostico(requestAtencionMedica.getDiagnostico());
        consultaEntity.get().setTratamiento(requestAtencionMedica.getTratamiento());
        consultaEntity.get().setNotasMedicas(requestAtencionMedica.getNotasMedicas());
        consultaEntity.get().setFechaModificacion(CurrentTime.getTimestamp());
        consultaEntity.get().setUsuarioModificacion(username);

        ConsultaMedicaEntity saveConsulta = consultaMedicaRepository.save(consultaEntity.get());

        List<RequestProcedimiento> listaProcedimientoEntities = requestAtencionMedica.getProcedimientos();
        for(RequestProcedimiento requestProcedimiento : listaProcedimientoEntities){

            ProcedimientoMedicoEntity procedimiento = ProcedimientoMedicoEntity.builder()
                    .tipoProcedimiento(requestProcedimiento.getTipoProcedimiento())
                    .notasProcedimiento(requestProcedimiento.getNotasProcedimiento())
                    .fechaProcedimiento(CurrentTime.getTimestamp())
                    .fechaCreacion(CurrentTime.getTimestamp())
                    .usuarioCreacion(username)
                    .estado(Constants.STATUS_ACTIVE)
                    .consulta(saveConsulta)
                    .build();
            procedimientoMedicoRepository.save(procedimiento);
        }

        return new ResponseBase(200, "Atualizacion exitosa.", saveConsulta);

    }


    @Override //Eliminación lógica de la consulta médica y sus procedimientos asociados.
    public ResponseBase DeleteConsultaMedicaOut(Long id, String username) {
        Optional<ConsultaMedicaEntity> getConsulta = consultaMedicaRepository.findById(id);
        if(getConsulta.isPresent() && getConsulta.get().getTriaje().getFechaTriaje()==null){
            List<ProcedimientoMedicoEntity> listaProcedimientos = procedimientoMedicoRepository
                    .findByConsulta(getConsulta.get());

            for(ProcedimientoMedicoEntity procedimiento : listaProcedimientos){
                procedimiento.setFechaEliminacion(CurrentTime.getTimestamp());
                procedimiento.setUsuarioEliminacion(username);
                procedimiento.setEstado(Constants.STATUS_INACTIVE);

//                procedimientoMedicoRepository.save(procedimiento);
            }
            procedimientoMedicoRepository.saveAll(listaProcedimientos);

            getConsulta.get().setFechaEliminacion(CurrentTime.getTimestamp());
            getConsulta.get().setUsuarioEliminacion(username);
            getConsulta.get().setEstado(Constants.STATUS_INACTIVE);

            ConsultaMedicaEntity saveConsulta = consultaMedicaRepository.save(getConsulta.get());

            return  new ResponseBase(200, "Registro eliminado", getConsulta.get());
        }
        return new ResponseBase(404, "Registro no encontrado o paciente ya paso por triaje.", null);
    }




    private ConsultaMedicaDTO BuildConsultaMedicaDTO(ConsultaMedicaEntity consultaEntity){

        TriajeDTO triajeDTO = genericMapper.mapTriajeEntityToTriajeDTO(consultaEntity.getTriaje());


        List<ProcedimientoMedicoEntity> listaProcedimientos = procedimientoMedicoRepository
                .findByConsulta(consultaEntity);
        List<ProcedimientoDTO> listaProcedimientoDTO = new ArrayList<>();
        for(ProcedimientoMedicoEntity procedimiento : listaProcedimientos){
            ProcedimientoDTO procedimientoDTO = genericMapper.mapProcedimientoMedEntityToProcedimientoMedDTO(procedimiento);
            listaProcedimientoDTO.add(procedimientoDTO);
        }

        return ConsultaMedicaDTO.builder()
                .idConsulta(consultaEntity.getIdConsulta())
                .fechaConsulta(consultaEntity.getFechaConsulta())
                .sintomas(consultaEntity.getSintomas())
                .diagnostico(consultaEntity.getDiagnostico())
                .tratamiento(consultaEntity.getTratamiento())
                .notasMedicas(consultaEntity.getNotasMedicas())
                .nombreSeguro(consultaEntity.getNombreSeguro())
                .coberturaSeguro(consultaEntity.getCoberturaSeguro())
                .doctor(consultaEntity.getDoctor().getNombre()+" "+consultaEntity.getDoctor().getApellido())
                .paciente(consultaEntity.getPaciente().getNombre()+" "+consultaEntity.getPaciente().getApellido())
                .triaje(triajeDTO)
                .procedimientos(listaProcedimientoDTO)
                .build();
    }
}








