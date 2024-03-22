package com.grupo1.domain.aggregates.request;


import com.grupo1.domain.aggregates.dto.ProcedimientoDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RequestAtencionMedica {

    @NotNull(message = "Se requiere el id de la consulta")
    @NotEmpty(message = "Se requiere el id de la consulta")
    private Long idConsulta;
    private String sintomas;
	private String diagnostico;
    private String tratamiento;
    private String notasMedicas;

    List<RequestProcedimiento> procedimientos;




}
