package com.grupo1.application.controller;


import com.grupo1.application.util.ErrorMessage;
import com.grupo1.application.util.FormatMessage;
import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestAtencionMedica;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.ConsultaMedicaServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/consulta")
@RequiredArgsConstructor
public class ConsultaMedicaController {

    private final ConsultaMedicaServiceIn consultaMedicaServiceIn;
    private final VerifyToken verifyToken;
    private final FormatMessage formatMessage;


    @GetMapping("/audit/{id}")
    public ResponseBase buscarConsultaMedicaEntity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                     @PathVariable Long id ){
        //Add roles autorizados aquí
//        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  consultaMedicaServiceIn.BuscarConsultaMedicaEntityIn(id);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @GetMapping("/audit/procedimiento/{idConsulta}")
    public ResponseBase BuscarAllProcedimientoMedicoEntity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                   @PathVariable Long idConsulta){
        //Add roles autorizados aquí
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  consultaMedicaServiceIn.BuscarAllProcedimientoMedicoEntityIn(idConsulta);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @GetMapping("/find/{id}") // id porque busco un unico analisis
    public ResponseBase buscarConsultaMedicaDTO(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
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
            return  consultaMedicaServiceIn.BuscarConsultaMedicaDtoIn(id);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @GetMapping("/all")
    public ResponseBase buscarAllEnableConsultaMedicaDTO(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        //Add roles autorizados aquí
        verifyToken.addRole("SYSTEM");
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("USER");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  consultaMedicaServiceIn.BuscarAllEnableConsultaMedicaDtoIn();
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    //Crea una consulta y triaje vacíos
    @PostMapping
    public ResponseBase registerConsultaMedica(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @Valid @RequestBody RequestRegister requestRegister, BindingResult result){
        if(result.hasErrors()){
            return new ResponseBase(400, this.formatMessage.FormatMessage(result), null );
        }
        //Add roles autorizados aquí
        verifyToken.addRole("USER");
//        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  consultaMedicaServiceIn.RegisterConsultaMedicaIn(requestRegister, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }


    /// Atención de consulta y registro de procedimientos
    @PutMapping("")
    public ResponseBase updateAtencionConsultaMedica(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                    @Valid @RequestBody RequestAtencionMedica requestAtencionCM,
                                                     BindingResult result){
        if(result.hasErrors()){
            return new ResponseBase(400, this.formatMessage.FormatMessage(result), null );
        }
        //Add roles autorizados aquí
        verifyToken.addRole("DOCTOR");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  consultaMedicaServiceIn.UpdateAtencionConsultaMedicaIn(requestAtencionCM, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }


    //Borra consulta y procedimientos asociados a la consulta.
    @DeleteMapping("/{id}")
    public ResponseBase deleteConsultaMedica(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long id){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("USER");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  consultaMedicaServiceIn.DeleteConsultaMedicaIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

}
