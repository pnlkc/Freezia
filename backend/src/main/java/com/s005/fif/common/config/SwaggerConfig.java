package com.s005.fif.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(
				new Info()
					.title("Fridge는 Free지 API")
					.description("삼성 DA 연계 프로젝트")
					.version("1.0.0")
			)
			.components(new Components()
				.addSecuritySchemes("Bearer Token",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.in(SecurityScheme.In.HEADER)
						.name(HttpHeaders.AUTHORIZATION)
						.scheme("bearer")
						.bearerFormat("JWT")))
			.addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
	}

}
