package com.grupo1.msexternalapi.domain.ports.out;

import com.grupo1.msexternalapi.domain.response.ResponseReniec;

public interface ReniecServiceOut {

    ResponseReniec getDataFromReniecOut(String numDoc);
}
