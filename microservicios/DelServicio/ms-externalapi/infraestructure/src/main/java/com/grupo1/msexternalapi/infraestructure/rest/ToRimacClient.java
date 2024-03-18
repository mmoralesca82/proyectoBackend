package com.grupo1.msexternalapi.infraestructure.rest;


import com.grupo1.msexternalapi.domain.response.ResponseRimac;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "client-rimac", url ="http://localhost:9000/rimac/api/v1/")
public interface ToRimacClient {

//    @GetMapping("/find")
//    ResponseRimac getInfoRimac(@RequestBody RequestRimac requestRimac,
//                               @RequestHeader("Authorization") String authorizationHeader);
    @GetMapping("/find")
    ResponseRimac getInfoRimac(@RequestParam String documento, @RequestParam String cobertura,
                               @RequestHeader("Authorization") String authorizationHeader);

}
