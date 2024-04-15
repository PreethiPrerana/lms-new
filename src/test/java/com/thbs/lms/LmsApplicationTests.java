package com.thbs.lms;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LmsApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> LmsApplication.main(new String[] {}));
	}

}
