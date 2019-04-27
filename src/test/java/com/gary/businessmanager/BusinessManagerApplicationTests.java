package com.gary.businessmanager;

import com.gary.businessmanager.dao.UserRepository;
import com.gary.businessmanager.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessManagerApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setName("admin");
		user.setEmail("admin@admin.com");
		user.setIdentity("employee");
		user.setPassword("123456");
		User savedUser = userRepository.save(user);
		System.out.println(savedUser);
	}

}
