package com.grupo1.infraestructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.domain.aggregates.constants.Constants;
import com.grupo1.domain.aggregates.dto.ContactoEmergenciaDTO;
import com.grupo1.domain.aggregates.dto.PacienteDTO;
import com.grupo1.domain.aggregates.request.RequestPaciente;
import com.grupo1.domain.aggregates.response.MsExternalToReniecResponse;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.PacienteServiceOut;
import com.grupo1.infraestructure.entity.*;
import com.grupo1.infraestructure.mapper.GenericMapper;
import com.grupo1.infraestructure.repository.ContactoEmergenciaRepository;
import com.grupo1.infraestructure.repository.DirecccionRepository;
import com.grupo1.infraestructure.repository.PacienteRepository;
import com.grupo1.infraestructure.rest.client.ToMSExternalApi;
import com.grupo1.infraestructure.util.CurrentTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PacienteAdapter implements PacienteServiceOut {

    private final PacienteRepository pacienteRepository;
    private final DirecccionRepository direcccionRepository;
    private final ContactoEmergenciaRepository contactoEmergenciaRepository;
    private final GenericMapper genericMapper;
    private final ToMSExternalApi toMSExternalApi;


    @Override
    public ResponseBase buscarDoctorOut(String numDoc) {
        Optional<PacienteEntity> getPaciente = pacienteRepository.findByNumDocumento(numDoc);
        if (getPaciente.isPresent()) {
            return new ResponseBase(302, "Informacion encotrada con exito", getPaciente);
        }

        return new ResponseBase(404, "No se encontro la informacion", null);
    }

    @Override
    public ResponseBase buscarAllEnableDoctorOut() {
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        List<PacienteEntity> pacienteEntities = pacienteRepository.findByEstado(true);
        for (PacienteEntity paciente : pacienteEntities) {
            PacienteDTO pacienteDTO = genericMapper.mapPacienteEntityToPacienteDTO(paciente);
            pacienteDTOList.add(pacienteDTO);
        }
        return new ResponseBase(302, "Informacion encotrada con exito", pacienteDTOList);
    }

    @Override
    public ResponseBase registerPacienteOut(RequestPaciente requestPaciente, String username) {
        ///////////////////Validar campos obligatorios ///////////////////////////////////////////
        if (requestPaciente.getNumDocumento() == null || requestPaciente.getNumDocumento().isEmpty() ||
                requestPaciente.getFechaNacimiento() == null || requestPaciente.getFechaNacimiento().isEmpty() ||
                requestPaciente.getGenero() == null || requestPaciente.getGenero().isEmpty() ||
                requestPaciente.getTelefono() == null || requestPaciente.getTelefono().isEmpty() ||
                requestPaciente.getDireccion().getVia() == null || requestPaciente.getDireccion().getVia().isEmpty() ||
                requestPaciente.getDireccion().getNumeroPredio() == null || requestPaciente.getDireccion().getNumeroPredio().toString().isEmpty() ||
                requestPaciente.getDireccion().getDistrito() == null || requestPaciente.getDireccion().getDistrito().isEmpty() ||
                requestPaciente.getDireccion().getProvincia() == null || requestPaciente.getDireccion().getProvincia().isEmpty() ||
                requestPaciente.getDireccion().getDepartamento() == null || requestPaciente.getDireccion().getDepartamento().isEmpty()) {
            return new ResponseBase(406, "Datos obligatorios incompletos.", null);
        }
        ////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////Validar formato de fecha de nacimiento///////////////////////////
        Date date = verifyFormatDateFromRequest(requestPaciente.getFechaNacimiento());
        if (date == null)
            return new ResponseBase(406,
                    "Formato de fecha incorrecto. Ingresar formato dd/MM/yyyy.", null);
        ////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////Validar si el paciente existe en base de datos ////////////////////
        Optional<PacienteEntity> getPaciente = pacienteRepository.findByNumDocumento(requestPaciente.getNumDocumento());
        if (getPaciente.isPresent()) {
            return new ResponseBase(406, "Ya existe un registro paciente con el numero de documento " +
                    requestPaciente.getNumDocumento(), null);
        }
        ////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////Validación de contactos a grabar///////////////////////////
        ResponseForValidationContactos getInfoConactos = new ResponseForValidationContactos();
        if (requestPaciente.getContactos() != null && !requestPaciente.getContactos().isEmpty()) {
                getInfoConactos = validationOfContactosFromRequestToSave(requestPaciente.getContactos(), username);
        }
        ////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////Buscar DNI en Reniec///////////////////// ///////////////////////
        MsExternalToReniecResponse getInfoReniec = toMSExternalApi.getInfoExtReniec(requestPaciente.getNumDocumento());
        if (getInfoReniec.getApellidoMaterno() == null) {
            return new ResponseBase(406, "El documento " + requestPaciente.getNumDocumento() +
                    " no existe en Reniec.", null);
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        DirecccionEntity direccion = genericMapper.mapDireccionDtoToDireccionEntity(requestPaciente.getDireccion());
        direccion.setUsuarioCreacion(username);
        direccion.setFechaCreacion(CurrentTime.getTimestamp());
        direccion.setEstado(Constants.STATUS_ACTIVE);
        direccion = direcccionRepository.save(direccion);


        PacienteEntity paciente = PacienteEntity.builder()
                .numDocumento(requestPaciente.getNumDocumento())
                .genero(requestPaciente.getGenero())
                .telefono(requestPaciente.getTelefono())
                .fechaNacimiento(date)
                .nombre(getInfoReniec.getNombres())
                .apellido(getInfoReniec.getApellidoPaterno() + " " + getInfoReniec.getApellidoMaterno())
                .usuarioCreacion(username)
                .fechaCreacion(CurrentTime.getTimestamp())
                .estado(Constants.STATUS_ACTIVE)
                .direccion(direccion)
                .build();

        if (!getInfoConactos.listaContactosEntities.isEmpty()) {
            paciente.setContactoEmergencia(getInfoConactos.listaContactosEntities);
        }
        paciente = pacienteRepository.save(paciente);

        return new ResponseBase(201,
                "Paciente registrado con exito." + getInfoConactos.mensajeContactos, paciente);

    }

    @Override
    public ResponseBase updatePacienteOut(RequestPaciente requestPaciente, String username) {
        /////////////////////////Validar campos obligatorios ///////////////////////////////////
        if (requestPaciente.getNumDocumento() == null || requestPaciente.getNumDocumento().isEmpty()) {
            return new ResponseBase(406, "Se requiere numero de documento para actualizar registro.", null);
        }
        ////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////Validar si el paciente existe en base de datos ////////////////////
        Optional<PacienteEntity> getPaciente = pacienteRepository.findByNumDocumento(requestPaciente.getNumDocumento());
        if (getPaciente.isEmpty()) {
            return new ResponseBase(406, "No existe un registro paciente con el numero de documento " +
                    requestPaciente.getNumDocumento(), null);
        }
        ////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////Validar formato de fecha de nacimiento///////////////////////////
        Date date = new Date();
        if (!(requestPaciente.getFechaNacimiento() == null || requestPaciente.getFechaNacimiento().isEmpty())) {
            if ((date = verifyFormatDateFromRequest(requestPaciente.getFechaNacimiento())) == null) {
                return new ResponseBase(406,
                        "Formato de fecha incorrecto. Ingresar formato dd/MM/yyyy.", null);
            }
            ///actualizo fecha de nacimiento
            getPaciente.get().setFechaNacimiento(date);
        }
        ////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////Validación los nuevos contactos////////////////////////////////////
        ResponseForValidationContactos getInfoConactos = new ResponseForValidationContactos();
        if (requestPaciente.getContactos() != null && !requestPaciente.getContactos().isEmpty()) {
           getInfoConactos = validationOfContactosFromRequestToSave(requestPaciente.getContactos(), username);
        }
        ///// Actualizar los estados de los contactos retirados de paciente si ya no tienen asignados pacientes.
        boolean toDisable = true;
        for(ContactoEmergenciaEntity oldContacto : getPaciente.get().getContactoEmergencia()){
            for(ContactoEmergenciaEntity newContacto : getInfoConactos.listaContactosEntities) {
                if (Objects.equals(oldContacto, newContacto)) {
                    toDisable = false;
                    break;
                }
            }
            if(toDisable && oldContacto.getPacientes().size()==1) {

                oldContacto.setFechaModificacion(CurrentTime.getTimestamp());
                oldContacto.setUsuarioModificacion(username);
                oldContacto.setEstado(Constants.STATUS_INACTIVE);
                contactoEmergenciaRepository.save(oldContacto);
            }
            toDisable=true;

        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        if(!(requestPaciente.getGenero()==null|| requestPaciente.getGenero().isEmpty()))
            getPaciente.get().setGenero(requestPaciente.getGenero());
        if(!(requestPaciente.getTelefono()==null|| requestPaciente.getTelefono().isEmpty()))
            getPaciente.get().setTelefono(requestPaciente.getTelefono());
        /// Update de dirección
        if(!(requestPaciente.getDireccion().getVia()==null || requestPaciente.getDireccion().getVia().isEmpty()))
            getPaciente.get().getDireccion().setVia(requestPaciente.getDireccion().getVia());
        if(!(requestPaciente.getDireccion().getNumeroPredio()==null || requestPaciente.getDireccion().getNumeroPredio().toString().isEmpty()))
            getPaciente.get().getDireccion().setNumeroPredio(requestPaciente.getDireccion().getNumeroPredio());
        if(!(requestPaciente.getDireccion().getInterior()==null || requestPaciente.getDireccion().getInterior().isEmpty()))
            getPaciente.get().getDireccion().setInterior(requestPaciente.getDireccion().getInterior());
        if(!(requestPaciente.getDireccion().getReferencia()==null || requestPaciente.getDireccion().getReferencia().isEmpty()))
            getPaciente.get().getDireccion().setReferencia(requestPaciente.getDireccion().getReferencia());
        if(!(requestPaciente.getDireccion().getDistrito()==null || requestPaciente.getDireccion().getDistrito().isEmpty()))
            getPaciente.get().getDireccion().setDistrito(requestPaciente.getDireccion().getDistrito());
        if(!(requestPaciente.getDireccion().getProvincia()==null || requestPaciente.getDireccion().getProvincia().isEmpty()))
            getPaciente.get().getDireccion().setProvincia(requestPaciente.getDireccion().getProvincia());
        if(!(requestPaciente.getDireccion().getDepartamento()==null || requestPaciente.getDireccion().getDepartamento().isEmpty()))
            getPaciente.get().getDireccion().setDepartamento(requestPaciente.getDireccion().getDepartamento());
        if (!getInfoConactos.listaContactosEntities.isEmpty()) {
            getPaciente.get().setContactoEmergencia(getInfoConactos.listaContactosEntities);
        }
        getPaciente.get().setFechaModificacion(CurrentTime.getTimestamp());
        getPaciente.get().setUsuarioModificacion(username);

        PacienteEntity savePaciente = pacienteRepository.save(getPaciente.get());


        return new ResponseBase(201,
                "Paciente registrado con exito." + getInfoConactos.mensajeContactos,
                savePaciente);

    }


    @Override
    public ResponseBase deletePacienteOut(String numDoc, String username) {
        Optional<PacienteEntity> findPaciente = pacienteRepository.findByNumDocumento(numDoc);
        if (findPaciente.isPresent()) {
            findPaciente.get().setEstado(Constants.STATUS_INACTIVE);
            findPaciente.get().setUsuarioEliminacion(username);
            findPaciente.get().setFechaEliminacion(CurrentTime.getTimestamp());

            return new ResponseBase(202, "Registro eliminado",
                    pacienteRepository.save(findPaciente.get()));
        }
        return new ResponseBase(404, "No se encontro al doctor.", null);
    }

    private Date verifyFormatDateFromRequest(String date) {
        try {
            DateFormat formateador = new SimpleDateFormat("dd/M/yy");
            return formateador.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    private ResponseForValidationContactos validationOfContactosFromRequestToSave(Set<ContactoEmergenciaDTO> listaContactosDto, String username) {
        Set<ContactoEmergenciaEntity> listaContactosEntities = new HashSet<>();
        StringBuilder mensajeContactos = new StringBuilder();
        for (ContactoEmergenciaDTO contacto : listaContactosDto) {
            if (contacto.getNombre() == null || contacto.getNombre().isEmpty() ||
                    contacto.getApellidoPaterno() == null || contacto.getApellidoPaterno().isEmpty() ||
                    contacto.getApellidoMaterno() == null || contacto.getApellidoMaterno().isEmpty() ||
                    contacto.getTelefono() == null || contacto.getTelefono().isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    mensajeContactos.append(" /// Contacto: ").append(objectMapper.writeValueAsString(contacto)).
                            append(" no registrado a paciente por datos obligatorios incompletos.");
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                Optional<ContactoEmergenciaEntity> getContacto = contactoEmergenciaRepository.
                        findByTelefono(contacto.getTelefono());
                if (getContacto.isPresent()) {
                    if (contacto.getNombre().equals(getContacto.get().getNombre()) &&
                            contacto.getApellidoPaterno().equals(getContacto.get().getApellidoPaterno()) &&
                            contacto.getApellidoMaterno().equals(getContacto.get().getApellidoMaterno())) {
                        getContacto.get().setUsuarioModificacion(username);
                        getContacto.get().setFechaModificacion(CurrentTime.getTimestamp());
                        getContacto.get().setEstado(Constants.STATUS_ACTIVE);
                        listaContactosEntities.add(getContacto.get());
                    } else {
                        mensajeContactos.append(" /// Contacto con telefono ").append(contacto.getTelefono()).
                                append(" existe en base de datos y no se asignó a paciente porque no coinciden con datos ingresados.");
                        //                        listaContactosDto.remove(contacto);
                    }
                } else {
                    ContactoEmergenciaEntity contactoEmerg = genericMapper.mapContactoDtoToContactoEntity(contacto);
                    contactoEmerg.setUsuarioCreacion(username);
                    contactoEmerg.setFechaCreacion(CurrentTime.getTimestamp());
                    contactoEmerg.setEstado(Constants.STATUS_ACTIVE);

                    listaContactosEntities.add(contactoEmergenciaRepository.save(contactoEmerg));
                }
            }

        }
        ResponseForValidationContactos respuesta = new ResponseForValidationContactos();
        respuesta.listaContactosEntities = listaContactosEntities;
        respuesta.mensajeContactos = mensajeContactos;
        return respuesta;
    }

    private static class ResponseForValidationContactos {
        StringBuilder mensajeContactos = new StringBuilder();
        Set<ContactoEmergenciaEntity> listaContactosEntities = new HashSet<>();
    }

}