package com.zam.uanet;

import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UanetApplicationTests {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		//UserEntity user = userService.getUser(new ObjectId("662ecc338217ba847346b799"));
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		//userService.updateUser(user);
	}

}
