package sanchez.sanchez.sergio.rest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import sanchez.sanchez.sergio.dto.request.JwtAuthenticationRequestDTO;
import sanchez.sanchez.sergio.rest.response.AuthenticationResponseCode;
import sanchez.sanchez.sergio.rest.response.ResponseStatus;

public class BaseAuthorizationControllerTest {
	
	private static Logger logger = LoggerFactory.getLogger(BaseAuthorizationControllerTest.class);

	@Value("#{'${api.base.path}' + '${api.version}' + '${jwt.route.authentication.path}' + '/'}")
	private String loginEndPoint;
	
	protected MockMvc mockMvc;
	
	@Autowired
    protected ObjectMapper objectMapper;
	
	@Autowired
    protected WebApplicationContext wac;
	
	@Value("${jwt.token.header}")
	private String tokenHeader;
	
	private String accessToken;
	
	@Before
	public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        String jsonContent = objectMapper.writeValueAsString(new JwtAuthenticationRequestDTO("federico@gmail.com", "bisite00"));

		MockHttpServletResponse response = mockMvc
			.perform(
					MockMvcRequestBuilders.post(loginEndPoint)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent)
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.response_code_name").value(AuthenticationResponseCode.AUTHENTICATION_SUCCESS.name()))
			.andExpect(jsonPath("$.response_status").value(ResponseStatus.SUCCESS.name()))
			.andExpect(jsonPath("$.response_http_status").value(HttpStatus.OK.name()))
			.andExpect(jsonPath("$.response_code").value(AuthenticationResponseCode.AUTHENTICATION_SUCCESS.getResponseCode()))
			.andExpect(jsonPath("$.response_data.token").isNotEmpty())
			.andExpect(jsonPath("$.response_data.token").isString())
			.andReturn().getResponse();
		
		String bodyContent = response.getContentAsString();
		
		logger.debug("Body Content: " + bodyContent);
		
		final String regex = "\\{\"token\":\"(\\S+)\"\\}";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(bodyContent);
		
		if(matcher.find())
			accessToken = matcher.group(1);
		
		logger.debug("Access Token: " + accessToken);
		
		Assert.assertNotNull("the accessToken must not be null!",
                this.accessToken);
      
	}
	
	protected MockHttpServletRequestBuilder encodeAuthorizationAccessToken(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder.header("Authorization", String.format("%s %s", tokenHeader, accessToken));
    }

}
