package com.grupo1.domain.ports.in;


import com.grupo1.domain.aggregates.request.RequestPaciente;
import com.grupo1.domain.aggregates.response.ResponseBase;


public interface PacienteServiceIn {
    ResponseBase registerPacienteIn(RequestPaciente requestPaciente, String username);

    ResponseBase  updatePacienteIn(RequestPaciente requestPaciente, String username);

    ResponseBase deletePacienteIn(String numDoc, String username);
}
