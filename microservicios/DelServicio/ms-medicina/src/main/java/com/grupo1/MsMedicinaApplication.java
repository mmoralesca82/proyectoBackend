package com.grupo1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition( info = @Info( title = "MS-MEDICINA", version = "1.0", description = "Servicio consultas medicas." ) )
public class MsMedicinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMedicinaApplication.class, args);
	}

}
