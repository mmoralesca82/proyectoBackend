package com.codigo.apigateway.service;


import com.codigo.apigateway.request.SignInRequest;
import com.codigo.apigateway.response.AuthenticationResponse;
import com.codigo.apigateway.response.VerifyResponse;

public interface AuthenticationService {


    AuthenticationResponse signin(SignInRequest iniciarSesionRequest);

    VerifyResponse getRoles(String token);


}
