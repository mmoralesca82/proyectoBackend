package com.grupo1.domain.ports.out;

import com.grupo1.domain.aggregates.response.MsSecurityResponse;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

public interface VerifySecurityServiceOut {

    Optional<MsSecurityResponse> verifyByTokenOut(String token);
}
