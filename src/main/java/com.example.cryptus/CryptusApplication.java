package com.example.cryptus;

import com.example.cryptus.dao.AssetDaoJdbc;
import com.example.cryptus.service.CoinGeckoAPIService;
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




	}
}


