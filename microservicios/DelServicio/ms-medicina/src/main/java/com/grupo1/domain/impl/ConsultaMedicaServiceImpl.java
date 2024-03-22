package com.grupo1.domain.impl;


import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestAtencionMedica;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.ConsultaMedicaServiceIn;
import com.grupo1.domain.ports.out.ConsultaMedicaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultaMedicaServiceImpl implements ConsultaMedicaServiceIn {

    private final ConsultaMedicaServiceOut consultaMedicaServiceOut;
    @Override
    public ResponseBase BuscarConsultaMedicaEntityIn(Long id) {
        return consultaMedicaServiceOut.BuscarConsultaMedicaEntityOut(id);
    }

    @Override
    public ResponseBase BuscarAllProcedimientoMedicoEntityIn(Long idConsulta) {
        return consultaMedicaServiceOut.BuscarAllProcedimientoMedicoEntityOut(idConsulta);
    }

    @Override
    public ResponseBase BuscarConsultaMedicaDtoIn(Long id) {
        return consultaMedicaServiceOut.BuscarConsultaMedicaDtoOut(id);
    }

    @Override
    public ResponseBase BuscarAllEnableConsultaMedicaDtoIn() {
        return consultaMedicaServiceOut.BuscarAllEnableConsultaMedicaDtoOut();
    }

    @Override
    public ResponseBase RegisterConsultaMedicaIn(RequestRegister requestRegister, String username) {
        return consultaMedicaServiceOut.RegisterConsultaMedicaOut(requestRegister, username);
    }

    @Override
    public ResponseBase UpdateAtencionConsultaMedicaIn(RequestAtencionMedica requestAtencionCM, String username) {
        return consultaMedicaServiceOut.UpdateAtencionConsultaMedicaOut(requestAtencionCM,username);
    }


    @Override
    public ResponseBase DeleteConsultaMedicaIn(Long id, String username) {
        return consultaMedicaServiceOut.DeleteConsultaMedicaOut(id, username);
    }
}
