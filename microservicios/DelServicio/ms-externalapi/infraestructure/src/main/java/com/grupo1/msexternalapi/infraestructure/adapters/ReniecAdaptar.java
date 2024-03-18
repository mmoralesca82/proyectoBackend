package com.grupo1.msexternalapi.infraestructure.adapters;

import com.grupo1.msexternalapi.domain.response.ResponseReniec;
import com.grupo1.msexternalapi.domain.ports.out.ReniecServiceOut;
import com.grupo1.msexternalapi.infraestructure.rest.ReniecClient;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReniecAdaptar implements ReniecServiceOut {

    private final ReniecClient reniec;

    @Value("${token.apireniec}")
    private String tokenApiReniec;

    @Override
    public ResponseReniec getDataFromReniecOut(String numDoc) {
        String authorization = "Bearer "+ tokenApiReniec;
        try {
            ResponseReniec responseReniec = reniec.getInfoReniec(numDoc, authorization);
            return responseReniec;
        }catch (Exception e){
            return new ResponseReniec();
        }
    }
}
