package com.grupo1.msexternalapi.domain.ports.out;

import com.grupo1.msexternalapi.domain.response.ResponseRimac;

public interface ToRimacServiceOut {

    ResponseRimac getDataFromInsuranceOut(String numDoc, String cobertura);
}
