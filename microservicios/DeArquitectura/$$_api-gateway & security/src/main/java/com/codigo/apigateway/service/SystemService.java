package com.codigo.apigateway.service;


import com.codigo.apigateway.entity.UsuarioEntity;
import com.codigo.apigateway.request.SystemRequest;
import org.springframework.http.ResponseEntity;

public interface SystemService {

    ResponseEntity<UsuarioEntity> createUsuario(SystemRequest usuarioRequest);
    ResponseEntity<UsuarioEntity> getUsuarioById(Long id, String subjectFromToken);
    ResponseEntity<UsuarioEntity> updateUsuario(Long id, SystemRequest usuarioRequest, String subjectFromToken);
    ResponseEntity<UsuarioEntity> deleteUsuario(Long id);

}
