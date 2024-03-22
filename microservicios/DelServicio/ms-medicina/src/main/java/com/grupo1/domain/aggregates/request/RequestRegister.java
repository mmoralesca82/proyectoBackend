package com.grupo1.domain.aggregates.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RequestRegister {


    private String fechaConsulta; //Requerido dd/MM/yyyy HH:mm

    @NotNull(message = "Se requiere el id del doctor")
    @NotEmpty(message = "Se requiere el id del doctor")
    private Long idDoctor;

    @NotNull(message = "Se requiere el id del paciente")
    @NotEmpty(message = "Se requiere el id del paciente")
    private Long idPaciente;

}
