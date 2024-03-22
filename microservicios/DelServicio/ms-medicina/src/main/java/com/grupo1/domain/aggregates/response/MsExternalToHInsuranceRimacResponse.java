package com.grupo1.domain.aggregates.response;

import lombok.Data;

@Data
public class MsExternalToHInsuranceRimacResponse {

    private String nomAseguradora;
    private String tipoCobertura;
    private Float cobertura;

}
