package com.grupo1.application.controller;

import com.grupo1.application.util.VerifyToken;
import com.grupo1.domain.aggregates.dto.NombreAnalisisDTO;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.NombreAnalisisServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/analisis")
@RequiredArgsConstructor
public class NombreAnalisisController {

    private final NombreAnalisisServiceIn nombreAnalisisServiceIn;

    private final VerifyToken verifyToken;

    @PostMapping
    public ResponseBase registerNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                               @RequestBody NombreAnalisisDTO nombreAnalisisDTO){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.registerNombreAnalisisIn(nombreAnalisisDTO, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @PutMapping
    public ResponseBase updateNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                           @RequestBody NombreAnalisisDTO nombreAnalisisDTO){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.updateNombreAnalisisIn(nombreAnalisisDTO, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteNombreAnalisis(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                           @PathVariable Long id){
        //Add roles autorizados aquí
        verifyToken.addRole("ADMIN");
        verifyToken.addRole("SYSTEM");
        //////////////////////////
        String username = verifyToken.verifyToken(token);
        if(username != null){
            return  nombreAnalisisServiceIn.deleteNombreAnalisisIn(id, username);
        }
        return new ResponseBase(403,"No autorizado.",null);

    }
}
