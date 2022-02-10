package com.fsk.microservice.autoparking.masterdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class MasterDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterDataApplication.class, args);
	}

}
