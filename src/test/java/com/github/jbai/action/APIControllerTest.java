package com.github.jbai.action;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jbai.TestConfig;
import com.github.jbai.WebConfig;
import com.github.jbai.entity.User;
import com.github.jbai.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = { TestConfig.class, WebConfig.class })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class APIControllerTest {
	
	private MockMvc mockMvc;

	private ObjectMapper mapper;	
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private UserService userService;
	
    @Before
    public void setup() {
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mapper = new ObjectMapper();
    }	
	
	@Test
	public void testRegisterUser() throws Exception {
		User user = new User();
		user.setName("1");
		user.setLoginName("1");
		user.setPassword("password");
		String jsonUser = mapper.writeValueAsString(user);
		
		this.mockMvc.perform(post("/api/register").contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).content(jsonUser))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").exists());
	}

}
