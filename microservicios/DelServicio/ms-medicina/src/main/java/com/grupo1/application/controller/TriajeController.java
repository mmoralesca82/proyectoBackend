package com.grupo1.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.application.util.ErrorMessage;
import com.grupo1.application.util.FormatMessage;
import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.request.RequestTriaje;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.TriajeServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/triaje")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "API MS-MEDICINA / TRIAJE",
                version = "1.0",
                description = "Atencion de triaje."
        )
)
public class TriajeController {

    private final TriajeServiceIn triajeServiceIn;
    private final VerifyToken verifyToken;
    private final FormatMessage formatMessage;


    @Operation(summary = "Busqueda de triaje por id con retorno de entity, permitido solo para SYSTEM")
    @GetMapping("/audit/{id}")
    public ResponseBase buscarTriajeEntity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                   @PathVariable Long id ){
        //Add roles autorizados aquí
//        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  triajeServiceIn.BuscarTriajeEntityIn(id);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @Operation(summary = "Busqueda de triaje por id con retorno de DTO, permitido para ADMIN, USER, DOCTOR y NURSE.")
    @GetMapping("/find/{id}")
    public ResponseBase buscarTriajeDTO(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @PathVariable Long id){
        //Add roles autorizados aquí
//        verifyToken.addRole("ADMIN");
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("USER");
        verifyToken.addRole("DOCTOR");
        verifyToken.addRole("NURSE");

        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  triajeServiceIn.BuscarTriajeDtoIn(id);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @Operation(summary = "Busqueda de todos los triajes con estado habilitado con retorno de DTOs, permitido para ADMIN, SYSTEM, USER Y NURSE.")
    @GetMapping("/all")
    public ResponseBase buscarAllEnableTriajeDTO(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        verifyToken.addRole("USER");
        verifyToken.addRole("NURSE");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  triajeServiceIn.BuscarAllEnableTriajeDtoIn();
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @Operation(summary = "Registro de atencion de triaje, permitido para NURSE.")
    /// Atención de triaje
    @PutMapping()
    public ResponseBase updateAtencionTriaje(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @Valid @RequestBody RequestTriaje requestTriaje, BindingResult result){
        if(result.hasErrors()){
            return new ResponseBase(400, this.formatMessage.FormatMessage(result), null );
        }
        //Add roles autorizados aquí
        verifyToken.addRole("NURSE");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  triajeServiceIn.UpdateTriajeIn(requestTriaje, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @Operation(summary = "Eliminado lógico de triaje, permitido para ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseBase deleteTriaje(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @PathVariable Long id){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  triajeServiceIn.DeleteTriajeIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }





}
