package com.bertolini.price_tracker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceTrackerApiApplication.class, args);
	}
}
