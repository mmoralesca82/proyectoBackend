package com.grupo1.msexternalapi.application;

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
public class MsExternalapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsExternalapiApplication.class, args);
	}

}
