package com.travel.travelbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
	    org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.class,
	    org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.class,
	    org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.class
	})
@EnableScheduling
public class TravelbookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelbookingsystemApplication.class, args);
	}

}
