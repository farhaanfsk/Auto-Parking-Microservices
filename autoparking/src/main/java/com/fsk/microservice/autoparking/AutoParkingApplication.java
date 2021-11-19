package com.fsk.microservice.autoparking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Auto-Parking API"
		, description = "API Definitions for Auto parking microservices", version = "1.0"))
public class AutoParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoParkingApplication.class, args);
	}

}
