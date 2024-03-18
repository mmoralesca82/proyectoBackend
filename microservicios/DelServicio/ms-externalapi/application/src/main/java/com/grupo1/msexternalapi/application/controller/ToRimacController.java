package com.grupo1.msexternalapi.application.controller;


import com.grupo1.msexternalapi.domain.ports.in.ToRimacServiceIn;
import com.grupo1.msexternalapi.domain.response.ResponseRimac;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/insurance")
@RequiredArgsConstructor
public class ToRimacController {

    private final ToRimacServiceIn toRimacServiceIn;

    // http://ip:port/ms-externalapi/v1/insurance/rimac?documento={numeroDNI}&cobertura={cobertura}
    @GetMapping("/rimac")
    public ResponseRimac getInfoFromInsurance
            (@RequestParam(required = true, defaultValue = "null") String documento,
             @RequestParam (required = true, defaultValue = "null") String cobertura){

        return toRimacServiceIn.getDataFromInsuranceIn(documento,cobertura);
    }
}



