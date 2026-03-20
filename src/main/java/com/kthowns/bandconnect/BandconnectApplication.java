package com.kthowns.bandconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BandconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BandconnectApplication.class, args);
	}

}
