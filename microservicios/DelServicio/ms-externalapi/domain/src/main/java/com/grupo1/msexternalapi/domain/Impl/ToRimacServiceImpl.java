package com.grupo1.msexternalapi.domain.Impl;

import com.grupo1.msexternalapi.domain.ports.in.ToRimacServiceIn;
import com.grupo1.msexternalapi.domain.ports.out.ToRimacServiceOut;
import com.grupo1.msexternalapi.domain.response.ResponseRimac;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToRimacServiceImpl implements ToRimacServiceIn {

    private final ToRimacServiceOut toRimacServiceOut;

    @Override
    public ResponseRimac getDataFromInsuranceIn(String numDoc, String cobertura) {
        return toRimacServiceOut.getDataFromInsuranceOut(numDoc,cobertura);
    }
}

