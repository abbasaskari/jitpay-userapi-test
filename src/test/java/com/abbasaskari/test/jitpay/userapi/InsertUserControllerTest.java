package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the insertUser method through controller
 */
@SpringBootTest
class InsertUserControllerTest {
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	private static final String uri = "/api/user/insert";

	/**
	 * Map objects to json
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	/**
	 * Map objects from json
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	/**
	 * Test insertUser method through controller
	 * @throws Exception
	 */
	@Test
	public void insertUser() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email44@jitpay.com", "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserResponseModel userResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserResponseModel.class);

		assertNotNull( userResponseModel.getUserId());
		assertNotNull( userResponseModel.getCreatedOn());
	}

	/**
	 * Test insertUser method through controller with duplicate email
	 * @throws Exception
	 */
	@Test
	public void insertUserWithDuplicateEmail() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel1 = new UserRequestModel("User1Email21@jitpay.com", "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel1))).andReturn();

		UserRequestModel userRequestModel2 = new UserRequestModel("User1Email21@jitpay.com", "User2 FirstName", "User2 SecondName");

		MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel2))).andReturn();

		assertEquals(500, mvcResult2.getResponse().getStatus());

		String expectedMessage = "Email is duplicate.";
		String actualMessage = mvcResult2.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with null email
	 * @throws Exception
	 */
	@Test
	public void insertUserWithNullEmail() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel(null, "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with email size zero
	 * @throws Exception
	 */
	@Test
	public void insertUserWithEmailSize0() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("", "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email size is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with wrong email format
	 * @throws Exception
	 */
	@Test
	public void insertUserWithWrongEmailFormat() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email", "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email format  is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with null firstName
	 * @throws Exception
	 */
	@Test
	public void insertUserWithNullFirstName() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email23@jitpay.com", null, "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "First name is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with firstName size zero
	 * @throws Exception
	 */
	@Test
	public void insertUserWithFirstNameSize0() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email24@jitpay.com", "", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "First name size is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with null secondName
	 * @throws Exception
	 */
	@Test
	public void insertUserWithNullSecondName() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email25@jitpay.com", "User1 FirstName", null);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Second name is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertUser method through controller with secondName size zero
	 * @throws Exception
	 */
	@Test
	public void insertUserWithSecondNameSize0() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email26@jitpay.com", "User1 FirstName", "");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Second name size is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

}
