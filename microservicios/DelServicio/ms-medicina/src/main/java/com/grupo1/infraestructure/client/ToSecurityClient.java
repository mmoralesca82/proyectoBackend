package com.grupo1.infraestructure.client;


import com.grupo1.domain.aggregates.response.MsSecurityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;



@FeignClient(name="MS-SECURITY", url  = "http://localhost:8095/ms-security/v1/autenticacion/")
public interface ToSecurityClient {

    @GetMapping("/verify")
    Optional<MsSecurityResponse> verifyToken (@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
