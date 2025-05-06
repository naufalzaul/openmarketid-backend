package com.naufalzaul.openmarketid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OpenmarketidApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenmarketidApplication.class, args);
	}

}
