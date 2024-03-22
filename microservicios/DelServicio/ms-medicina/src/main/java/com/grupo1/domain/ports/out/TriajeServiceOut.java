package com.grupo1.domain.ports.out;

import com.grupo1.domain.aggregates.request.RequestTriaje;
import com.grupo1.domain.aggregates.response.ResponseBase;

public interface TriajeServiceOut {

    ResponseBase BuscarTriajeEntityOut(Long id);

    ResponseBase BuscarTriajeDtoOut(Long id);

    ResponseBase BuscarAllEnableTriajeDtoOut();

    ResponseBase UpdateTriajeOut(RequestTriaje requestTriaje, String  username);

    ResponseBase DeleteTriajeOut(Long id,String  username);

}
