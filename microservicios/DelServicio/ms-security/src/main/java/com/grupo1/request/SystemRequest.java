package com.grupo1.request;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemRequest {

    private Long idUsuario;
    private String username;
    private String password;
    private String email;
    private String telefono;
    Set<String> roles; //Los roles seran recibidos explicitamente (ADMIN, USER, SUPER, etc) y no por Id.
}
