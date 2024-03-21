package com.grupo1.domain.impl;

import com.grupo1.domain.aggregates.response.MsSecurityResponse;
import com.grupo1.domain.ports.in.VerifySecurityServiceIn;
import com.grupo1.domain.ports.out.VerifySecurityServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerifySecurityServiceImpl implements VerifySecurityServiceIn {

    private  final VerifySecurityServiceOut verifySecurityServiceOut;

    @Override
    public Optional<MsSecurityResponse> verifyByTokenIn(String token) {
        return verifySecurityServiceOut.verifyByTokenOut(token);
    }
}
