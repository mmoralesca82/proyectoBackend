package com.codigo.apigateway.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class VerifyResponse {

    private Integer code;
    private String message;
    private String username;
    private Set<String> roles;
}
