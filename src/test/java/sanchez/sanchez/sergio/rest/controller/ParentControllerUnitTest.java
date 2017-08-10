package sanchez.sanchez.sergio.rest.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.runners.MethodSorters;
import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.rest.config.RestConfig;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;
import sanchez.sanchez.sergio.rest.response.ResponseStatus;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParentControllerUnitTest extends BaseControllerUnitTest {

	private static Logger logger = LoggerFactory.getLogger(ParentControllerUnitTest.class);
	
	
	@Value("#{'${api.base.path}' + '${api.version}' + '${api.parents.base}' + '/all'}")
	private String allParentsEndPoint;
	
	@Value("#{'${api.base.path}' + '${api.version}' + '${api.parents.base}' + '/'}")
	private String saveParentEndPoint;
	
	private RegisterParentDTO registerParentDTO;
	
	@Before
	public void setupData() {
		this.registerParentDTO = new RegisterParentDTO("test11", "User Test", 45, "test@gmail.com", "bisite00", "bisite00");
	}
	
	@Test
	public void test001verifyRegisterParent() throws Exception {
		
		String jsonContent = asJsonString(registerParentDTO);
		
		logger.debug("JSON to send -> " + jsonContent);
	
		mockMvc
			.perform(
				MockMvcRequestBuilders.post(saveParentEndPoint)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(jsonContent)
					.accept(MediaType.APPLICATION_JSON_UTF8)
			)		
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.response_code_name").value(ParentResponseCode.PARENT_REGISTERED_SUCCESSFULLY.name()))
			.andExpect(jsonPath("$.response_status").value(ResponseStatus.SUCCESS.name()))
			.andExpect(jsonPath("$.response_http_status").value(HttpStatus.OK.name()))
			.andExpect(jsonPath("$.response_code").value(ParentResponseCode.PARENT_REGISTERED_SUCCESSFULLY.getResponseCode()));
		
	}
	
	
	@Test
	public void test002verifyThatThereAreThreeParents() throws Exception {
		mockMvc
			.perform(MockMvcRequestBuilders.get(allParentsEndPoint)
					.accept(MediaType.APPLICATION_JSON_UTF8))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.response_code_name").value(ParentResponseCode.ALL_PARENTS.name()))
			.andExpect(jsonPath("$.response_status").value(ResponseStatus.SUCCESS.name()))
			.andExpect(jsonPath("$.response_http_status").value(HttpStatus.OK.name()))
			.andExpect(jsonPath("$.response_code").value(ParentResponseCode.ALL_PARENTS.getResponseCode()))
			.andExpect(jsonPath("$.response_data", hasSize(3)));
	}
	
	
	
}
