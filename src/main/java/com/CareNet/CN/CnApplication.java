package com.CareNet.CN;

import com.CareNet.CN.util.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CnApplication {

	public static void main(String[] args) {
		// Load .env file
		EnvLoader.load(".env");
		SpringApplication.run(CnApplication.class, args);
	}

}
