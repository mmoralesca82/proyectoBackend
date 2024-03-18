package com.grupo1.infraestructure.adapter;

import com.grupo1.domain.aggregates.request.RequestPaciente;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.PacienteServiceOut;
import com.grupo1.infraestructure.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteAdapter implements PacienteServiceOut {

    private final PacienteRepository pacienteRepository;


    @Override
    public ResponseBase registerPacienteOut(RequestPaciente requestPaciente, String username) {
        return new ResponseBase(200, "Desde  PacienteAdapter/register, " +
                "registrado por "+username, null);
    }

    @Override
    public ResponseBase updatePacienteOut(RequestPaciente requestPaciente, String username) {
        return new ResponseBase(200, "Desde  PacienteAdapter/update, " +
                "registrado por "+username, null);
    }

    @Override
    public ResponseBase deletePacienteOut(String numDoc, String username) {
        return new ResponseBase(200, "Desde  PacienteAdapter/delete, " +
                "registrado por "+username, null);
    }
}
