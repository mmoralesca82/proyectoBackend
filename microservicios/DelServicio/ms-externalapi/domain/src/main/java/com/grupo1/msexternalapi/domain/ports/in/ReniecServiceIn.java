package com.grupo1.msexternalapi.domain.ports.in;

import com.grupo1.msexternalapi.domain.response.ResponseReniec;



public interface ReniecServiceIn {

    ResponseReniec getDataFromReniecIn(String numDoc);

}
