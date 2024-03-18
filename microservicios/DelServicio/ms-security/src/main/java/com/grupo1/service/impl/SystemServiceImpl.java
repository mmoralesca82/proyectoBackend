package com.grupo1.service.impl;


import com.grupo1.entity.RolEntity;
import com.grupo1.entity.UsuarioEntity;
import com.grupo1.repository.RolRepository;
import com.grupo1.repository.UsuarioRepository;
import com.grupo1.request.SystemRequest;
import com.grupo1.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;


    @Override
    public ResponseEntity<UsuarioEntity> createUsuario(SystemRequest usuarioRequest) {
        Optional<UsuarioEntity> existingUser = usuarioRepository.findByUsername(usuarioRequest.getUsername());
        if (existingUser.isPresent() || usuarioRequest.getUsername() == null ||
                usuarioRequest.getPassword()== null || usuarioRequest.getEmail()== null ||
                usuarioRequest.getTelefono()== null) {
            return ResponseEntity.badRequest().body(null);
        }
        return getUsuarioResponseEntity(0L, usuarioRequest, "create");
    }

    @Override
    public ResponseEntity<UsuarioEntity> getUsuarioById(Long id, String subjectFromToken) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UsuarioEntity> updateUsuario(Long id, SystemRequest usuarioRequest, String subjectFromToken) {
        Optional<UsuarioEntity> existingUsuario = usuarioRepository.findById(id);
        if (existingUsuario.isPresent()) {
            if (usuarioRequest.getUsername() != null && // se agrega esta linea para permitir modificar otros campos que
                    //no sean el userName, como password por ejemplo.
                    !usuarioRequest.getUsername().equals(existingUsuario.get().getUsername())) {
                Optional<UsuarioEntity> userWithNewUsername = usuarioRepository.findByUsername(usuarioRequest.getUsername());
                if (userWithNewUsername.isPresent()) {
                    return ResponseEntity.badRequest().body(null);
                }
            }
            return getUsuarioResponseEntity(id, usuarioRequest, "update");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UsuarioEntity> deleteUsuario(Long id) { // solo ADMIN podrá eliminar de manera lógica
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setEnabled(false);
            usuario.get().setAccountnonlocked(false);
            usuario.get().setCredentialsnonexpired(false);
            usuario.get().setAccountnonexpire(false);
            usuarioRepository.save(usuario.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<UsuarioEntity> getUsuarioResponseEntity(Long id, SystemRequest usuarioRequest, String typeRequest) {
        Set<RolEntity> assignedRoles = new HashSet<>();
        UsuarioEntity usuario = new UsuarioEntity();
        if(usuarioRequest.getRoles() != null){
            for (String roles : usuarioRequest.getRoles()) { //Los roles vienen explícitos (ADMIN, USER, etc)
                Optional<RolEntity> rol = rolRepository.findByNombreRol(roles);
                //return ResponseEntity.badRequest().body(null);
                rol.ifPresent(assignedRoles::add);
            }
        }else if (typeRequest.equals("create")){
            assignedRoles.add(rolRepository.findByNombreRol("USER").orElse(null));
        }

        if(typeRequest.equals("update")){usuario=usuarioRepository.findById(id).get();}
        if(usuarioRequest.getUsername() != null){usuario.setUsername(usuarioRequest.getUsername());}
        if(usuarioRequest.getPassword() != null){usuario.setPassword(new BCryptPasswordEncoder()
                .encode(usuarioRequest.getPassword()));}
        if(usuarioRequest.getEmail() != null){usuario.setEmail(usuarioRequest.getEmail());}
        if(usuarioRequest.getTelefono() != null){usuario.setTelefono(usuarioRequest.getTelefono());}
        if(!assignedRoles.isEmpty()){usuario.setRoles(assignedRoles);} // Asignamos los roles.

        UsuarioEntity updatedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

}
