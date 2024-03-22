package com.codigo.apigateway.controller;



import com.codigo.apigateway.entity.UsuarioEntity;
import com.codigo.apigateway.request.SystemRequest;
import com.codigo.apigateway.service.JWTService;
import com.codigo.apigateway.service.SystemService;
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
    public ResponseEntity<?> createUsuario(@RequestBody SystemRequest usuarioRequest) {
        return ResponseEntity.ok(systemService.createUsuario(usuarioRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                        @PathVariable Long id) {
        String subjectFromToken = getSubjectFromToken(token);
        return systemService.getUsuarioById(id, subjectFromToken);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> updateUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id,
                                                       @RequestBody SystemRequest usuarioRequest) {
        String subjectFromToken = getSubjectFromToken(token);
        return systemService.updateUsuario(id,usuarioRequest, subjectFromToken);
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
