package com.etz.bank_api;

import com.etz.bank_api.model.UserModel;
import com.etz.bank_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class BankApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}
}
