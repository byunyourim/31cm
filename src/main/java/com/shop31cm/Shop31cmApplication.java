package com.shop31cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Shop31cmApplication {

	public static void main(String[] args) {

		SpringApplication.run(Shop31cmApplication.class, args);
	}

}
