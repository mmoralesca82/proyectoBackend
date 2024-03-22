package com.grupo1.domain.ports.in;


import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestAtencionMedica;
import com.grupo1.domain.aggregates.response.ResponseBase;

public interface ConsultaMedicaServiceIn {

    ResponseBase BuscarConsultaMedicaEntityIn(Long id); //recive un long id

    ResponseBase BuscarAllProcedimientoMedicoEntityIn(Long idConsulta);

    ResponseBase BuscarConsultaMedicaDtoIn(Long id);

    ResponseBase BuscarAllEnableConsultaMedicaDtoIn();

    ResponseBase RegisterConsultaMedicaIn(RequestRegister requestRegister, String username);

    ResponseBase UpdateAtencionConsultaMedicaIn(RequestAtencionMedica requestAtencionCM, String username);

    ResponseBase DeleteConsultaMedicaIn(Long id, String username);


}
