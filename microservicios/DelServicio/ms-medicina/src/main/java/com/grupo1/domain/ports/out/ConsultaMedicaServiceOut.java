package com.grupo1.domain.ports.out;

import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestAtencionMedica;
import com.grupo1.domain.aggregates.response.ResponseBase;

public interface ConsultaMedicaServiceOut {

    ResponseBase BuscarConsultaMedicaEntityOut(Long id); //recive un long id

    ResponseBase BuscarAllProcedimientoMedicoEntityOut(Long idConsulta);

    ResponseBase BuscarConsultaMedicaDtoOut(Long id);

    ResponseBase BuscarAllEnableConsultaMedicaDtoOut();

    ResponseBase RegisterConsultaMedicaOut(RequestRegister requestRegister, String username);

    ResponseBase UpdateAtencionConsultaMedicaOut(RequestAtencionMedica requestAtencionCM, String username);

    ResponseBase DeleteConsultaMedicaOut(Long id, String username);

}
