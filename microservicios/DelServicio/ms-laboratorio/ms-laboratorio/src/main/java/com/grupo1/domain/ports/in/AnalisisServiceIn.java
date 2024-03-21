package com.grupo1.domain.ports.in;


import com.grupo1.domain.aggregates.request.RequestRegister;
import com.grupo1.domain.aggregates.request.RequestResultado;
import com.grupo1.domain.aggregates.response.ResponseBase;

public interface AnalisisServiceIn {

    ResponseBase BuscarAnalisisEntityIn(Long id); //recive un long id

    ResponseBase BuscarAnalisisDtoIn(Long id);

    ResponseBase BuscarAllEnableAnalisisDtoIn();

    ResponseBase RegisterAnalisisIn(RequestRegister requestRegister, String username);

    ResponseBase UpdateTomaMuestraAnalisisIn(Long id, String username);

    ResponseBase UpdateResultadoAnalisisIn(RequestResultado requestResultado, String username);

    ResponseBase DeleteAnalisisIn(Long id, String username);


}
