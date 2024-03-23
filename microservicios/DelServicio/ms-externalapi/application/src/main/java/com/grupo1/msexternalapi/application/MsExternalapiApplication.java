package com.grupo1.msexternalapi.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients("com.grupo1.*")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@ComponentScan("com.grupo1.*")
@EntityScan("com.grupo1.*")
@OpenAPIDefinition( info = @Info( title = "MS-EXTERNALAPI", version = "1.0", description = "Concentrador de consumo de APIs externas" ) )
public class MsExternalapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsExternalapiApplication.class, args);
	}

}
