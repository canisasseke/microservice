package com.zopenlab.microservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zopenlab.microservice.rest.HelloResource;

@RunWith(SpringRunner.class)
public class HelloResourceTest {

	
	private MockMvc mockMvc;
	
	@InjectMocks
	private HelloResource helloResource;
	
	@Before
	public void setUp() throws Exception{
		mockMvc=MockMvcBuilders.standaloneSetup(helloResource).build();
	}
	
	@Test
	public void testHelloWorld() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
									.andExpect(MockMvcResultMatchers.status().isOk())
									.andExpect(content().string("Hello World"));
		
	}
	@Test
	public void testHello_json() throws Exception {
		mockMvc.perform(get("/json").accept(MediaType.APPLICATION_JSON))
									.andExpect(jsonPath("$.title", Matchers.is("Greetings")))
									.andExpect(jsonPath("$.value", Matchers.is("Hello World")))
									.andExpect(jsonPath("$.*", Matchers.hasSize(2)));
									
	}
}
