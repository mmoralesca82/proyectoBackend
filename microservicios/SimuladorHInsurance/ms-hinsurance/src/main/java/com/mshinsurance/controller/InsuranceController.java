package com.mshinsurance.controller;

import com.mshinsurance.request.InsuranceRequest;
import com.mshinsurance.response.InsuranceResponse;
import com.mshinsurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

//    @GetMapping("/find")
//    InsuranceResponse findByNumDoc (@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody InsuranceRequest insuranceRequest){
//        return  insuranceService.askForInsured(insuranceRequest, token);
//    }

        @GetMapping("/find")
        InsuranceResponse findByNumDoc (@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam String documento,
                                        @RequestParam String cobertura) {
            InsuranceRequest insuranceRequest = new InsuranceRequest(documento, cobertura);
            return insuranceService.askForInsured(insuranceRequest, token);
    }

}
