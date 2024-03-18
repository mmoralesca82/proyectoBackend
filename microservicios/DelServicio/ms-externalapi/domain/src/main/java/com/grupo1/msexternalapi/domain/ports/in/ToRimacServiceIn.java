package com.grupo1.msexternalapi.domain.ports.in;

import com.grupo1.msexternalapi.domain.response.ResponseRimac;

public interface ToRimacServiceIn {

    ResponseRimac getDataFromInsuranceIn(String numDoc, String cobertura);

}
