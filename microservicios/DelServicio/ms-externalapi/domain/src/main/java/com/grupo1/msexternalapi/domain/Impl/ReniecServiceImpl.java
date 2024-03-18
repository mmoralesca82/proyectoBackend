package com.grupo1.msexternalapi.domain.Impl;

import com.grupo1.msexternalapi.domain.ports.in.ReniecServiceIn;
import com.grupo1.msexternalapi.domain.ports.out.ReniecServiceOut;
import com.grupo1.msexternalapi.domain.response.ResponseReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReniecServiceImpl implements ReniecServiceIn {

    private final ReniecServiceOut reniec;

    @Override
    public ResponseReniec getDataFromReniecIn(String numDoc) {
        return reniec.getDataFromReniecOut(numDoc);
    }
}
