package com.zam.uanet;

import com.zam.uanet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@EnableMongoRepositories
public class UanetApplication {

	public static void main(String[] args) {
		SpringApplication.run(UanetApplication.class, args);
	}

}
