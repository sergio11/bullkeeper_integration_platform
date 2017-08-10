package sanchez.sanchez.sergio.rest.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseControllerUnitTest {
	
	protected MockMvc mockMvc;
	
	@Autowired
    protected WebApplicationContext wac;
	
	
	@Before
	public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	/*
     * converts a Java object into JSON representation
     */
    protected String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
