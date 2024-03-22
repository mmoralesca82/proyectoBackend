package com.grupo1.domain.aggregates.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcedimientoDTO {

    private Long idProcMedico;
    private String tipoProcedimiento;
    private String notasProcedimiento;
    private Timestamp fechaProcedimiento;

}
