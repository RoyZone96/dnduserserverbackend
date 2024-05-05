package com.dndbackendlayer.dndbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Entity;

@SpringBootApplication
@EntityScan(basePackages = "com.dndbackendlayer.dndbackend.model")
public class DndbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DndbackendApplication.class, args);
	}

}
