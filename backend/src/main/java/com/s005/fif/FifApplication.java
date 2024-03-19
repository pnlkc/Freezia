package com.s005.fif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FifApplication {

	public static void main(String[] args) {
		SpringApplication.run(FifApplication.class, args);
	}

}
