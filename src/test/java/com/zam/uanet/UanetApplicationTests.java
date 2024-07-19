package com.zam.uanet;

import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.repositories.StudentRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UanetApplicationTests {

	@Autowired
	private StudentRepository studentRepository;

	@Test
	void contextLoads() {
		StudentEntity student = studentRepository.findById(new ObjectId("66734c126d0da510b59d88fe")).get();
		student.setPhoto(null);
		studentRepository.save(student);
	}
}
