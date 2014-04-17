package com.github.jbai.action;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.github.jbai.entity.User;

public class APIControllerTest {
	
	protected final RestTemplate template = new RestTemplate();
	
	@Test
	public void testRegisterUser() {
		User u = new User();
		u.setLoginName("loginname1");
		u.setName("name1");
		u.setPassword("password1");
		HttpEntity<User> requestEntity = new HttpEntity(u);
		ResponseEntity<String> result = template.postForEntity("http://localhost:8080/jbai/api/async/register", u, String.class);
		System.out.println(result);
		HttpStatus statusCode = result.getStatusCode();
		String code = result.getHeaders().get("x-code").get(0);
		String message = result.getHeaders().get("x-message").get(0);
		assertEquals(HttpStatus.OK, statusCode);
		assertEquals(code, "200");
		assertEquals(message, "success");
	}
	
	@Test
	public void testProfile() {
		User u = new User();
		u.setLoginName("loginname2");
		u.setName("name2");
		u.setPassword("password2");
		HttpEntity<User> requestEntity = new HttpEntity(u);
		ResponseEntity<String> result = template.postForEntity("http://localhost:8080/jbai/api/register", u, String.class);
		System.out.println(result);
		HttpStatus statusCode = result.getStatusCode();
		String code = result.getHeaders().get("x-code").get(0);
		String message = result.getHeaders().get("x-message").get(0);
		assertEquals(HttpStatus.OK, statusCode);
		assertEquals(code, "200");
		assertEquals(message, "success");
		
		
		
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("userId", result.getBody());
		parts.add("image", new FileSystemResource("D:\\source\\java\\spring-project-template\\src\\main\\webapp\\a.jpg"));

		result = template.postForEntity("http://localhost:8080/jbai/api/profile", parts, String.class);
		
		System.out.println(result);
		statusCode = result.getStatusCode();
		code = result.getHeaders().get("x-code").get(0);
		message = result.getHeaders().get("x-message").get(0);
		assertEquals(HttpStatus.OK, statusCode);
		assertEquals(code, "200");
		assertEquals(message, "success");
	}


}
