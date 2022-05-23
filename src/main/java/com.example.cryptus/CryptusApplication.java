package com.example.cryptus;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.PortefeuilleDAOJdbc;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CryptusApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(CryptusApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 1; i++) { // iets dergelijks
			System.out.println();
		}
	}

}
