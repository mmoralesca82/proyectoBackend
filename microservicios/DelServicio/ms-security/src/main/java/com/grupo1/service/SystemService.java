package com.grupo1.service;


import com.grupo1.entity.UsuarioEntity;
import com.grupo1.request.SystemRequest;
import org.springframework.http.ResponseEntity;

public interface SystemService {

    ResponseEntity<UsuarioEntity> createUsuario(SystemRequest usuarioRequest);
    ResponseEntity<UsuarioEntity> getUsuarioById(Long id, String subjectFromToken);
    ResponseEntity<UsuarioEntity> updateUsuario(Long id, SystemRequest usuarioRequest, String subjectFromToken);
    ResponseEntity<UsuarioEntity> deleteUsuario(Long id);

}
