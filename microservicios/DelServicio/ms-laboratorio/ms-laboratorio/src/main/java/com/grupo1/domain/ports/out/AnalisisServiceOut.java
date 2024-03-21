package com.grupo1.domain.ports.out;

import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestResultado;
import com.grupo1.domain.aggregates.response.ResponseBase;

public interface AnalisisServiceOut {

    ResponseBase BuscarAnalisisEntityOut(Long id); //recive un long id

    ResponseBase BuscarAnalisisDtoOut(Long id);

    ResponseBase BuscarAllEnableAnalisisDtoOut();

    ResponseBase RegisterAnalisisOut(RequestRegister requestRegister, String username);

    ResponseBase UpdateTomaMuestraAnalisisOut(Long id, String username);

    ResponseBase UpdateResultadoAnalisisOut(RequestResultado requestResultado, String username);

    ResponseBase DeleteAnalisisOut(Long id, String username);

}
