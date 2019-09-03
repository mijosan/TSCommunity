package com.dodge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dodge.board.service.EmailServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DodgeApplicationTests {

	@Autowired
	private EmailServiceImpl email;
	
	@Test
	public void contextLoads() {
		email.sendSimpleMessage("mijosan@naver.com", "test", "µÇ³É");
	}

}
