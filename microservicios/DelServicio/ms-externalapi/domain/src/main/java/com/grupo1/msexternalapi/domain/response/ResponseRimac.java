package com.grupo1.msexternalapi.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseRimac {

    private String nomAseguradora;
    private String tipoCobertura;
    private Double cobertura;


}
