package com.grupo1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition( info = @Info( title = "MS-LABORATORIO", version = "1.0", description = "Servicio analisis clinicos" ) )
public class MsLaboratorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLaboratorioApplication.class, args);
	}

}
