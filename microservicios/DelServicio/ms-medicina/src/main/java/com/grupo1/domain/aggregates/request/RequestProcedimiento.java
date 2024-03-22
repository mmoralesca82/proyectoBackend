package com.grupo1.domain.aggregates.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RequestProcedimiento {

    @NotNull(message = "Se requiere especificar el tipo de procedimiento.")
    @NotEmpty(message = "Se requiere especificar el tipo de procedimiento.")
    private String tipoProcedimiento;
    private  String notasProcedimiento;

}
