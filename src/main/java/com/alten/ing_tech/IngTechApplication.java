package com.alten.ing_tech;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "Swagger basic authentication", scheme = "basic",
		type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class IngTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngTechApplication.class, args);
	}

}
