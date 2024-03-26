package com.game.TicTacToe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication // This annotation encompasses @Configuration, @EnableAutoConfiguration, and @ComponentScan annotations with their default attributes
public class TicTacToeApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TicTacToeApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.address", "0.0.0.0"));
		app.run(args);
//		SpringApplication.run(TicTacToeApplication.class, args);
	}
}
