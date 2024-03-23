package com.grupo1.application.controller;


import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.dto.EspecialidadMedicaDTO;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.EspecialidadMedicaServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/especialidad")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "API MS-REGISTRO / ESPECIALIDAD MEDICA",
                version = "1.0",
                description = "Registro de especialidades medicas."
        )
)
public class EspecialidadMedicaController {

    private final EspecialidadMedicaServiceIn especialidadMedicaServiceIn;
    private final VerifyToken verifyToken;

    @Operation(summary = "Busqueda de especialidad medica por id con retorno de entity, permitido solo para SYSTEM")
    @GetMapping("/{especialidad}")
    public ResponseBase findEspecialidad(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @PathVariable String especialidad){
        //Add roles autorizados aquí
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  especialidadMedicaServiceIn.findEspecialidadMedicaIn(especialidad);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Busqueda de todas las especialidad medicas con estado habilitado con retorno de DTOs, permitido para SYSTEM Y ADMIN.")
    @GetMapping("/all")
    public ResponseBase findAllEnableEspecialidad(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  especialidadMedicaServiceIn.findAllEnableEspecialidadMedicaIn();
        }
        return new ResponseBase(403,"No autorizado.",null);
    }


    @Operation(summary = "Registrar especialidad medica, permitido para ADMIN.")
    @PostMapping
    public ResponseBase registerEspecialidad(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @RequestBody EspecialidadMedicaDTO especialidadMedicaDTO){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  especialidadMedicaServiceIn.registerEspecialidadMedicaIn(especialidadMedicaDTO, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Actualizar registro de especialidad medica, permitido para ADMIN.")
    @PutMapping
    public ResponseBase updateEspecialidad(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @RequestBody EspecialidadMedicaDTO especialidadMedicaDTO){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  especialidadMedicaServiceIn.updateEspecialidadMedicaIn(especialidadMedicaDTO, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

    @Operation(summary = "Eliminado lógico de especialidad medica, permitido para ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseBase deleteEspecialidad(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long id){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  especialidadMedicaServiceIn.deleteEspecialidadMedicaIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);
    }

}
