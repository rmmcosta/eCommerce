package com.example.demo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class SareetaApplicationTest {

	@Test
	public void contextLoads() {
	}

	@Test
	public void getBCryptPasswordEncoder() {
		SareetaApplication sareetaApplication = new SareetaApplication();
		assertNotNull(sareetaApplication.getBCryptPasswordEncoder());
	}

	@Test
	public void main() {
		SareetaApplication.main(new String[]{});
	}
}
