package com.dodge;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dodge.board.service.EmailServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DodgeApplicationTests {

	@Test
	public void contextLoads() {
		Random random = new Random();
		System.out.println(random.nextInt(100000));
	}

}
