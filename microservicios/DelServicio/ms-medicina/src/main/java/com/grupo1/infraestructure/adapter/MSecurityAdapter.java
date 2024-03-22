package com.grupo1.infraestructure.adapter;


import com.grupo1.domain.aggregates.response.MsSecurityResponse;
import com.grupo1.domain.ports.out.VerifySecurityServiceOut;
import com.grupo1.infraestructure.client.ToSecurityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MSecurityAdapter implements VerifySecurityServiceOut {

    private final ToSecurityClient toSecurityClient;

    @Override
    public Optional<MsSecurityResponse> verifyByTokenOut(String token) {
        return toSecurityClient.verifyToken(token);
    }
}

