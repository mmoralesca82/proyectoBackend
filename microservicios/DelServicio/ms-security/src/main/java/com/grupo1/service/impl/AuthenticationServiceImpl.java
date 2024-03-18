package com.grupo1.service.impl;

import com.grupo1.entity.UsuarioEntity;
import com.grupo1.repository.UsuarioRepository;
import com.grupo1.request.SignInRequest;
import com.grupo1.response.AuthenticationResponse;
import com.grupo1.response.VerifyResponse;
import com.grupo1.service.AuthenticationService;
import com.grupo1.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final SystemServiceImpl usuarioService;

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user = usuarioRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no valido"));

        var jwt = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse =  new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

    @Override
    public VerifyResponse getRoles(String subjectFromToken) {
        Optional<UsuarioEntity> findUser = usuarioRepository.findByUsername(subjectFromToken);
        if(findUser.isPresent()){
            Set<String> roles = findUser.get().getRolesNames();
            return new VerifyResponse(200, "allowed",
                    findUser.get().getUsername(),roles);

        }
        return new VerifyResponse(404, null, null,null);
    }


}
