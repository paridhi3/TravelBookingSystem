package com.travel.travelbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TravelbookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelbookingsystemApplication.class, args);
	}

}
