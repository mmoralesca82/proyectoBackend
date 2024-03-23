package com.grupo1.controller;


import com.grupo1.entity.UsuarioEntity;
import com.grupo1.request.SystemRequest;
import com.grupo1.service.JWTService;
import com.grupo1.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/system")
@RequiredArgsConstructor
public class SystemController {
    private final SystemService systemService;
    private  final JWTService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> createUsuario(@RequestBody SystemRequest systemRequest) {
        return ResponseEntity.ok(systemService.createUsuario(systemRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                        @PathVariable Long id) {
        String subjectFromToken = getSubjectFromToken(token);
        return systemService.getUsuarioById(id, subjectFromToken);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> updateUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id,
                                                       @RequestBody SystemRequest systemRequest) {
        String subjectFromToken = getSubjectFromToken(token);
        return systemService.updateUsuario(id,systemRequest, subjectFromToken);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return systemService.deleteUsuario(id);
    }

    private String getSubjectFromToken(String token){
        final String jwt = token.substring(7);
        return jwtService.extractUserName(jwt);
    }
}
