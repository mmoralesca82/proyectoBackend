package com.grupo1.domain.impl;

import com.grupo1.domain.aggregates.request.RequestTriaje;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.in.TriajeServiceIn;
import com.grupo1.domain.ports.out.TriajeServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TriajeServiceImpl implements TriajeServiceIn {

    private final TriajeServiceOut triajeServiceOut;

    @Override
    public ResponseBase BuscarTriajeEntityIn(Long id) {
        return triajeServiceOut.BuscarTriajeEntityOut(id);
    }

    @Override
    public ResponseBase BuscarTriajeDtoIn(Long id) {
        return triajeServiceOut.BuscarTriajeDtoOut(id);
    }

    @Override
    public ResponseBase BuscarAllEnableTriajeDtoIn() {
        return triajeServiceOut.BuscarAllEnableTriajeDtoOut();
    }

    @Override
    public ResponseBase UpdateTriajeIn(RequestTriaje requestTriaje, String username) {
        return triajeServiceOut.UpdateTriajeOut(requestTriaje, username);
    }

    @Override
    public ResponseBase DeleteTriajeIn(Long id, String username) {
        return triajeServiceOut.DeleteTriajeOut(id, username);
    }
}
