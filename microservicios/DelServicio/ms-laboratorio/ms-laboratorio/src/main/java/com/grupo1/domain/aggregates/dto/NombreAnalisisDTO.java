package com.grupo1.domain.aggregates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;





@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NombreAnalisisDTO {


    private Long idNombreAnalisis;
    private String analisis;
    private String complejidad;

}
