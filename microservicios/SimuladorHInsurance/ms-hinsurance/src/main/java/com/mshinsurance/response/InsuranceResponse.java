package com.mshinsurance.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InsuranceResponse {

    private Integer code;
    private String nomAseguradora;
    private String menssage;
    private String tipoCobertura;
    private Double cobertura;

}
