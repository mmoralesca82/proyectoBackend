package com.grupo1.domain.aggregates.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grupo1.infraestructure.entity.DoctorEntity;
import com.grupo1.infraestructure.entity.PacienteEntity;
import com.grupo1.infraestructure.entity.TriajeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaMedicaDTO {

    private Long idConsulta;
    private Date fechaConsulta;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private String notasMedicas;
    private String nombreSeguro;
    private Float coberturaSeguro;
    private String doctor;
    private String paciente;

    private TriajeDTO triaje;
    private List<ProcedimientoDTO> procedimientos;

}
