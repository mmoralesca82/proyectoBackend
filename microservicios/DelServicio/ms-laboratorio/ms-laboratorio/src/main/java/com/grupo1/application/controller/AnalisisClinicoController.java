package com.grupo1.application.controller;


import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestResultado;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.AnalisisServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analisis")
@RequiredArgsConstructor
public class AnalisisClinicoController {

    private final AnalisisServiceIn analisisServiceIn;
    private final VerifyToken verifyToken;


    @GetMapping("/audit/{id}") //es el analisis completo incluido auditoria // el id es de analisis
    public ResponseBase buscarAnalisisEntity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                     @PathVariable Long id ){
        //Add roles autorizados aquí
//        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.BuscarAnalisisEntityIn(id);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @GetMapping("/find/{id}") // id porque busco un unico analisis
    public ResponseBase buscarAnalisisDTO(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long id){
        //Add roles autorizados aquí
//        verifyToken.addRole("ADMIN");
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("LAB");
        verifyToken.addRole("USER");
        verifyToken.addRole("NURSE");

        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.BuscarAnalisisDtoIn(id);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @GetMapping("/all")
    public ResponseBase buscarAllEnableAnalisisDTO(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        verifyToken.addRole("LAB");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.BuscarAllEnableAnalisisDtoIn();
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @PostMapping
    public ResponseBase registerAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @RequestBody RequestRegister requestRegister){
        //Add roles autorizados aquí
        verifyToken.addRole("DOCTOR");
//        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.RegisterAnalisisIn(requestRegister, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @PutMapping("/muestra/{id}")// path de inicio de analisis
    public ResponseBase updateTomaMuestraAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @PathVariable Long id){
        //Add roles autorizados aquí
//        verifyToken.addRole("DOCTOR");
        verifyToken.addRole("LAB");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.UpdateTomaMuestraAnalisisIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @PutMapping("/resultado")// path de salida de resultado
    public ResponseBase updateResultadoAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @RequestBody RequestResultado requestResultado){
        //Add roles autorizados aquí
//        verifyToken.addRole("DOCTOR");
        verifyToken.addRole("LAB");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.UpdateResultadoAnalisisIn(requestResultado, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }


    @DeleteMapping("/{id}")
    public ResponseBase deleteAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long id){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
//        verifyToken.addRole("SYSTEM");
        verifyToken.addRole("DOCTOR");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  analisisServiceIn.DeleteAnalisisIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

}
