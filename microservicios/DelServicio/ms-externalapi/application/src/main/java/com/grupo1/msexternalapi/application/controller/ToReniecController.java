package com.grupo1.msexternalapi.application.controller;

import com.grupo1.msexternalapi.domain.response.ResponseReniec;
import com.grupo1.msexternalapi.domain.ports.in.ReniecServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@OpenAPIDefinition(
        info = @Info(
                title = "API MS-EXTERNALAPI",
                version = "1.0",
                description = "Conexion con API externa RENIEC"
        )
)
@RestController
@RequestMapping("/v1/reniec")
@RequiredArgsConstructor
public class ToReniecController {

    private final ReniecServiceIn reniecServiceIn;


    @Operation(summary = "Endpoint para obtener informacion desde reniec, requerido numero de documento.")
    // http://ip:port/ms-externalapi/v1/reniec/{numeroDNI}
    @GetMapping("/{numero}")
    public ResponseReniec getInfoFromReniec(@PathVariable String numero){
        return  reniecServiceIn.getDataFromReniecIn(numero);
    }
}
