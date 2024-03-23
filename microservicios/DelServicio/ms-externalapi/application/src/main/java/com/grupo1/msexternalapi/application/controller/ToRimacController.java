package com.grupo1.msexternalapi.application.controller;


import com.grupo1.msexternalapi.domain.ports.in.ToRimacServiceIn;
import com.grupo1.msexternalapi.domain.response.ResponseRimac;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@OpenAPIDefinition(
        info = @Info(
                title = "API MS-EXTERNALAPI",
                version = "1.0",
                description = "Conexion con API externa HINSURANCE"
        )
)
@RestController
@RequestMapping("/v1/insurance")
@RequiredArgsConstructor
public class ToRimacController {

    private final ToRimacServiceIn toRimacServiceIn;

    @Operation(summary = "Endpoint para obtener informacion desde aseguradora, requerido numero de documento y complejidad de procecimiento.")
    // http://ip:port/ms-externalapi/v1/insurance/rimac?documento={numeroDNI}&cobertura={cobertura}
    @GetMapping("/rimac")
    public ResponseRimac getInfoFromInsurance
            (@RequestParam(required = true, defaultValue = "null") String documento,
             @RequestParam (required = true, defaultValue = "null") String cobertura){

        return toRimacServiceIn.getDataFromInsuranceIn(documento,cobertura);
    }
}



