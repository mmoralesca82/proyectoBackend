package com.grupo1.infraestructure.adapter;

import com.grupo1.domain.aggregates.dto.NombreAnalisisDTO;
import com.grupo1.domain.aggregates.response.ResponseBase;
import com.grupo1.domain.ports.out.NombreAnalisisServiceOut;
import com.grupo1.infraestructure.repository.NombreAnalisisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NombreAnalisisAdapter implements NombreAnalisisServiceOut {

    private final NombreAnalisisRepository nombreAnalisisRepository;
    @Override
    public ResponseBase registerNombreAnalisisOut(NombreAnalisisDTO nombreAnalisisDTO, String username) {
        return new ResponseBase(200, "Desde  NombreAnalisisAdapter/register, " +
                "registrado por "+username, null);
    }

    @Override
    public ResponseBase updateNombreAnalisisOut(NombreAnalisisDTO nombreAnalisisDTO, String username) {
        return new ResponseBase(200, "Desde  NombreAnalisisAdapter/update, " +
                "registrado por "+username, null);
    }

    @Override
    public ResponseBase deleteNombreAnalisisOut(Long id, String username) {
        return new ResponseBase(200, "Desde  NombreAnalisisAdapter/delete, " +
                "registrado por "+username, null);
    }
}
