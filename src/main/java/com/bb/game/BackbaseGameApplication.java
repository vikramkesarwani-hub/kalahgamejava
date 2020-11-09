package com.bb.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author VK
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bb"})
public class BackbaseGameApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BackbaseGameApplication.class, args);
	}
	
}
