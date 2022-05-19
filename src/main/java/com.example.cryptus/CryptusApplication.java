package com.example.cryptus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptusApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CryptusApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) { // iets dergelijks
			System.out.println("Piet Pietersen");
		}
	}

}
