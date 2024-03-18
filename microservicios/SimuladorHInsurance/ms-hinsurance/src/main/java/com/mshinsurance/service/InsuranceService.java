package com.mshinsurance.service;

import com.mshinsurance.request.InsuranceRequest;
import com.mshinsurance.response.InsuranceResponse;


public interface InsuranceService {

    InsuranceResponse askForInsured(InsuranceRequest insuranceRequest, String token);
}
