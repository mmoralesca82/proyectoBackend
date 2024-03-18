package com.grupo1.msexternalapi.infraestructure.adapters;


import com.grupo1.msexternalapi.domain.ports.out.ToRimacServiceOut;
import com.grupo1.msexternalapi.domain.response.ResponseRimac;
import com.grupo1.msexternalapi.infraestructure.rest.ToRimacClient;
import com.grupo1.msexternalapi.infraestructure.util.RequestRimac;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToRimacAdapter implements ToRimacServiceOut {

    private final ToRimacClient rimac;

    @Value("${token.apirimac}")
    private String tokenApiRimac;

    @Override
    public ResponseRimac getDataFromInsuranceOut(String numDoc, String cobertura) {
        String authorization = "Bearer "+ tokenApiRimac;
        RequestRimac requestRimac = new RequestRimac(numDoc,cobertura);
        try {
//            ResponseRimac responseRimac = rimac.getInfoRimac(requestRimac, authorization);
            ResponseRimac responseRimac = rimac.getInfoRimac(numDoc, cobertura, authorization);
            return responseRimac;
        }catch (Exception e){
            return new ResponseRimac();
        }


    }
}
