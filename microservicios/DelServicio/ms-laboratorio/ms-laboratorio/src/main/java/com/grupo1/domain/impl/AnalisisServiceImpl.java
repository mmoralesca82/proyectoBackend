package com.grupo1.domain.impl;


import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestResultado;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.AnalisisServiceIn;
import com.grupo1.domain.ports.out.AnalisisServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalisisServiceImpl implements AnalisisServiceIn {

    private final AnalisisServiceOut analisisServiceOut;
    @Override
    public ResponseBase BuscarAnalisisEntityIn(Long id) {
        return analisisServiceOut.BuscarAnalisisEntityOut(id);
    }

    @Override
    public ResponseBase BuscarAnalisisDtoIn(Long id) {
        return analisisServiceOut.BuscarAnalisisDtoOut(id);
    }

    @Override
    public ResponseBase BuscarAllEnableAnalisisDtoIn() {
        return analisisServiceOut.BuscarAllEnableAnalisisDtoOut();
    }

    @Override
    public ResponseBase RegisterAnalisisIn(RequestRegister requestRegister, String username) {
        return analisisServiceOut.RegisterAnalisisOut(requestRegister, username);
    }

    @Override
    public ResponseBase UpdateTomaMuestraAnalisisIn(Long id, String username) {
        return analisisServiceOut.UpdateTomaMuestraAnalisisOut(id, username);
    }

    @Override
    public ResponseBase UpdateResultadoAnalisisIn(RequestResultado requestResultado, String username) {
        return analisisServiceOut.UpdateResultadoAnalisisOut(requestResultado, username);
    }

    @Override
    public ResponseBase DeleteAnalisisIn(Long id, String username) {
        return analisisServiceOut.DeleteAnalisisOut(id, username);
    }
}
