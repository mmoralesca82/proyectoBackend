package com.grupo1.application.controller;

import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.dto.NombreAnalisisDTO;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.NombreAnalisisServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/analisis")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "API MS-REGISTRO / NOMBRE DE ANALISIS",
                version = "1.0",
                description = "Registro de nombres de analisis clinicos."
        )
)
public class NombreAnalisisController {

    private final NombreAnalisisServiceIn nombreAnalisisServiceIn;

    private final VerifyToken verifyToken;

    @Operation(summary = "Busqueda de nombre de analisis por id con retorno de entity, permitido solo para SYSTEM")
    @GetMapping("/{nombreAnalisis}")
    public ResponseBase findNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @PathVariable String nombreAnalisis){
        //Add roles autorizados aquí
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.findNombreAnalisisIn(nombreAnalisis);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Busqueda de todas los nombres de analisis con estado habilitado con retorno de DTOs, permitido para SYSTEM Y ADMIN.")
    @GetMapping("/all")
    public ResponseBase findAllEnableNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.findAllEnableNombreAnalisisIn();
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Registrar nombre de analisis, permitido para ADMIN.")
    @PostMapping
    public ResponseBase registerNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                               @RequestBody NombreAnalisisDTO nombreAnalisisDTO){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.registerNombreAnalisisIn(nombreAnalisisDTO, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Actualizar registro de nombre de analisis, permitido para ADMIN.")
    @PutMapping
    public ResponseBase updateNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                           @RequestBody NombreAnalisisDTO nombreAnalisisDTO){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.updateNombreAnalisisIn(nombreAnalisisDTO, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Eliminado lógico de nombre de analisis, permitido para ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseBase deleteNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                           @PathVariable Long id){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.deleteNombreAnalisisIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

}
