package com.shopcartpro.shopcartpro_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ShopcartproBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopcartproBackendApplication.class, args);
	}

}
