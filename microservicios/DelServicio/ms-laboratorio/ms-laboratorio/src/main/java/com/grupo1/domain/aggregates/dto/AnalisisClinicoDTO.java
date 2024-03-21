package com.grupo1.domain.aggregates.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalisisClinicoDTO {

    private Long idAnalisis;
    private String nombreSeguro; //Obtenido con consulta al API HInsurance via MS-ExternalAPI
    private Float coberturaSeguro;//Obtenido con consulta al API HInsurance via MS-ExternalAPI
    private String resultado;
    private String usuarioRecepcion;
    private Timestamp fechaRecepcion;
    private String nombreDoctor;
    private String nombrePaciente;


    private NombreAnalisisDTO nombreAnalisis;
    private ConsultaMedicaDTO consulta;

}
