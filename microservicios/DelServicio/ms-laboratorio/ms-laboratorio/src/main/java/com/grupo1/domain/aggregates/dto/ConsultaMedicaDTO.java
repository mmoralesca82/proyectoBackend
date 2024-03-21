package com.grupo1.domain.aggregates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaMedicaDTO {

    private Timestamp fechaConsulta;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private String notasMedicas;

}
