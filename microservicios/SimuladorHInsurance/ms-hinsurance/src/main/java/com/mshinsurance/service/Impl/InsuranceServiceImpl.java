package com.mshinsurance.service.Impl;

import com.mshinsurance.entity.AseguradoEntity;
import com.mshinsurance.entity.TokenEntity;
import com.mshinsurance.repository.AseguradoRepository;
import com.mshinsurance.repository.TokenRepository;
import com.mshinsurance.request.InsuranceRequest;
import com.mshinsurance.response.InsuranceResponse;
import com.mshinsurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final AseguradoRepository aseguradoRepository;
    private final TokenRepository tokenRepository;

    @Override
    public InsuranceResponse askForInsured(InsuranceRequest insuranceRequest, String token) {

        String getToken = token.substring(7);
        Optional<TokenEntity> findToken = tokenRepository.findByToken(getToken);
        if(findToken.isEmpty() || token == null){
            return new InsuranceResponse(403,"RIMAC", "token not found","null",0.0);
        }


        Optional<AseguradoEntity> findAsegurado = aseguradoRepository.findByNumDoc(insuranceRequest.getNumDoc());
        if(findAsegurado.isPresent() && findAsegurado.get().getEnabled()){
            Double cobertura = 0.0;
            switch (insuranceRequest.getCobertura()){
                case "MED1":
                        cobertura=findAsegurado.get().getCobMed().getCobMed1();
                    break;
                case "MED2":
                        cobertura=findAsegurado.get().getCobMed().getCobMed2();
                    break;
                case "MED3":
                        cobertura=findAsegurado.get().getCobMed().getCobMed3();
                    break;
                case "LAB1":
                        cobertura=findAsegurado.get().getCobLab().getCobLab1();
                    break;
                case "LAB2":
                        cobertura=findAsegurado.get().getCobLab().getCobLab2();
                    break;
                case "LAB3":
                        cobertura=findAsegurado.get().getCobLab().getCobLab3();
                    break;
                default:


            }
            return new InsuranceResponse(200, "RIMAC","allow", insuranceRequest.getCobertura(), cobertura);


        }
        return new InsuranceResponse(404,"RIMAC","insured not found",insuranceRequest.getCobertura(),0.0 );
    }
}


