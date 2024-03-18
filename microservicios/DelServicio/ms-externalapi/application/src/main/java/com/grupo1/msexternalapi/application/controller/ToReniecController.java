package com.grupo1.msexternalapi.application.controller;

import com.grupo1.msexternalapi.domain.response.ResponseReniec;
import com.grupo1.msexternalapi.domain.ports.in.ReniecServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reniec")
@RequiredArgsConstructor
public class ToReniecController {

    private final ReniecServiceIn reniecServiceIn;


    // http://ip:port/ms-externalapi/v1/reniec/{numeroDNI}
    @GetMapping("/{numero}")
    public ResponseReniec getInfoFromReniec(@PathVariable String numero){
        return  reniecServiceIn.getDataFromReniecIn(numero);
    }
}
