package com.grupo1.domain.ports.in;

import com.grupo1.domain.aggregates.request.RequestTriaje;
import com.grupo1.domain.aggregates.response.ResponseBase;
import jdk.javadoc.doclet.Reporter;

public interface TriajeServiceIn {

    ResponseBase BuscarTriajeEntityIn(Long id);

    ResponseBase BuscarTriajeDtoIn(Long id);

    ResponseBase BuscarAllEnableTriajeDtoIn();

    ResponseBase UpdateTriajeIn(RequestTriaje requestTriaje, String  username);

    ResponseBase DeleteTriajeIn(Long id,String  username);

}
