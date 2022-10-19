package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the updateUser method through controller
 */
@SpringBootTest
class UpdateUserControllerTest {
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	UserBusinessService userBusinessService;

	private static final String uri = "/api/user/update";

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
	 * Test updateUser method through controller
	 * @throws Exception
	 */
	@Test
	public void updateUser() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email29@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel1 = userBusinessService.insert(userRequestModel);

		UserRequestModel userRequestMode2 = new UserRequestModel("UpdatedUser1Email296@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+userResponseModel1.getUserId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * Test updateUser method through controller with fake user
	 * @throws Exception
	 */
	@Test
	public void updateUserWithFakeUserId() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel1 = new UserRequestModel("User1email31@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel1 = userBusinessService.insert(userRequestModel1);

		UserRequestModel userRequestMode2 = new UserRequestModel("UpdatedUser1Email31@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestMode2))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "User not found.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with duplicate email
	 * @throws Exception
	 */
	@Test
	public void updateUserWithDuplicateEmail() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel1 = new UserRequestModel("User1email32@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel1 = userBusinessService.insert(userRequestModel1);

		UserRequestModel userRequestModel2 = new UserRequestModel("User1emai33@jitpay.com", "User2 FirstName", "User2 SecondName");
		UserResponseModel userResponseModel2 = userBusinessService.insert(userRequestModel2);

		UserRequestModel userRequestModel3 = new UserRequestModel(userRequestModel2.getEmail(), "Updated User1 FirstName", "Updated User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+userResponseModel1.getUserId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel3))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email is duplicate.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with same email
	 * @throws Exception
	 */
	@Test
	public void updateUserWithSameEmail() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email34@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserRequestModel userRequestMode2 = new UserRequestModel("User1Email34@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+userResponseModel.getUserId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * Test updateUser method through controller with null email
	 * @throws Exception
	 */
	@Test
	public void updateUserWithNullEmail() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel(null, "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with email size zero
	 * @throws Exception
	 */
	@Test
	public void updateUserWithEmailSize0() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("", "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email size is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with wrong email format
	 * @throws Exception
	 */
	@Test
	public void updateUserWithWrongEmailFormat() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email", "User1 FirstName", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Email format  is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with null firstName
	 * @throws Exception
	 */
	@Test
	public void updateUserWithNullFirstName() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email35@jitpay.com", null, "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "First name is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with firstName size zero
	 * @throws Exception
	 */
	@Test
	public void updateUserWithFirstNameSize0() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email36@jitpay.com", "", "User1 SecondName");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "First name size is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with null secondName
	 * @throws Exception
	 */
	@Test
	public void updateUserWithNullSecondName() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email37@jitpay.com", "User1 FirstName", null);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Second name is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through controller with secondName size zero
	 * @throws Exception
	 */
	@Test
	public void updateUserWithSecondNameSize0() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email38@jitpay.com", "User1 FirstName", "");

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Second name size is wrong.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}
}
