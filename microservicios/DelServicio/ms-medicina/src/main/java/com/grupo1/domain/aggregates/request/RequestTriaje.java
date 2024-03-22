package com.grupo1.domain.aggregates.request;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class RequestTriaje {

    @NotNull(message = "Se requiere el número de consulta")
    @NotEmpty(message = "Se requiere el número de consulta")
    private  Long idConsulta; // La base del triaje es la consulta

    @NotNull(message = "Se requiere la tempCorporal")
    @NotEmpty(message = "Se requiere la tempCorporal")
    private Double tempCorporal;

    @NotNull(message = "Se requiere la presionArterial")
    @NotEmpty(message = "Se requiere la presionArterial")
    private String presionArterial;

    @NotNull(message = "Se requiere la satOxigeno")
    @NotEmpty(message = "Se requiere la satOxigeno")
    private Integer satOxigeno;

    private String sintoma;
    private String observaciones;

}
