package com.grupo1.controller;

import com.grupo1.request.SignInRequest;
import com.grupo1.response.AuthenticationResponse;
import com.grupo1.response.VerifyResponse;
import com.grupo1.service.AuthenticationService;
import com.grupo1.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/autenticacion")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private  final JWTService jwtService;


    @PostMapping("/login") // Logeo
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }


    @GetMapping("verify")  // metodo usado por los micros servicios para obtener roles desde el token
    public VerifyResponse getRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        final String subjectFromToken = jwtService.extractUserName(token.substring(7));
        return authenticationService.getRoles(subjectFromToken);
    }

}
