package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.domain.model.LocationModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationRequestModel;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the insertLocation method through controller
 */
@SpringBootTest
class InsertLocationControllerTest {
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;
	@Autowired
	UserBusinessService userBusinessService;

	private static final String uri = "/api/location/insert";

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
	 * Test insertLocation method through controller
	 * @throws Exception
	 */
	@Test
	public void insertLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email17@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel(-10.1,10.2));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userLocationRequestModel))).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * Test insertLocation method through controller with fake user
	 * @throws Exception
	 */
	@Test
	public void insertLocationWithFakeUser() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email18@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel("Test 1", new Date(), new LocationModel((double)-10,(double)10));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userLocationRequestModel))).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "User not found.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertLocation method through controller with null userId
	 * @throws Exception
	 */
	@Test
	public void insertLocationWithNullUserId() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(null, new Date(), new LocationModel((double)-10,(double)10));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userLocationRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "User id is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertLocation method through controller with null CreatedOn
	 * @throws Exception
	 */
	@Test
	public void insertLocationWithNullCreatedOn() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel("Test 1", null, new LocationModel((double)-10,(double)10));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userLocationRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Created on is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test insertLocation method through controller with null location
	 * @throws Exception
	 */
	@Test
	public void insertLocationWithNullLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel("Test 1", new Date(), null);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(userLocationRequestModel))).andReturn();


		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "Location is null.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}
}
