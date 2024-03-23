package com.grupo1.application.controller;

import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.request.RequestPaciente;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.PacienteServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paciente")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "API MS-REGISTRO / PACIENTE",
                version = "1.0",
                description = "Registro de pacientes."
        )
)
public class PacienteController {

    private final PacienteServiceIn pacienteServiceIn;
    private final VerifyToken verifyToken;

    @Operation(summary = "Busqueda de paciente por id con retorno de entity, permitido solo para SYSTEM")
    @GetMapping("/{numDoc}")
    public ResponseBase buscarPaciente(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                     @PathVariable String numDoc){
        //Add roles autorizados aquí
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  pacienteServiceIn.buscarDoctorIn(numDoc);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Busqueda de todos los pacientes con estado habilitado con retorno de DTOs, permitido para SYSTEM, ADMIN y USER.")
    @GetMapping("/all")
    public ResponseBase buscarAllEnableDoctor(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        //Add roles autorizados aquí
        verifyToken.addRole("USER");
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  pacienteServiceIn.buscarAllEnableDoctorIn();
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Registrar paciente, permitido para USER.")
    @PostMapping
    public ResponseBase registerPaciente(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @RequestBody RequestPaciente requestPaciente){
        //Add roles autorizados aquí
        verifyToken.addRole("USER");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  pacienteServiceIn.registerPacienteIn(requestPaciente, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Actualizar registro de paciente, permitido para USER.")
    @PutMapping
    public ResponseBase updatePaciente(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @RequestBody RequestPaciente requestPaciente){
        //Add roles autorizados aquí
        verifyToken.addRole("USER");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  pacienteServiceIn.updatePacienteIn(requestPaciente, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Eliminado lógico de paciente, permitido para ADMIN.")
    @DeleteMapping("/{numDoc}")
    public ResponseBase deletePaciente(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                          @PathVariable String numDoc){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  pacienteServiceIn.deletePacienteIn(numDoc, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

}
