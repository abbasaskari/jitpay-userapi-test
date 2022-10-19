package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.domain.model.*;
import com.abbasaskari.test.jitpay.userapi.service.business.LocationBusinessService;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import com.fasterxml.jackson.core.JsonParseException;
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
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the getLastLocationByUserId method through controller
 */
@SpringBootTest
class GetLastLocationByUserIdControllerTest {
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;
	@Autowired
	private UserBusinessService userBusinessService;
	@Autowired
	private LocationBusinessService locationBusinessService;

	/**
	 *
	 */
	private static final String uri = "/api/location/get-last-location-by-user-id";

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
	 * Test getLastLocationByUserId method through controller with fake user
	 * @throws Exception
	 */
	@Test
	public void getWithFakeUser() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email1@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+"Test 1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "User not found.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test getLastLocationByUserId method through controller without location
	 * @throws Exception
	 */
	@Test
	public void getWithoutLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email2@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+userResponseModel.getUserId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserLocationResponseModel userLocationResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserLocationResponseModel.class);

		assertEquals(userLocationResponseModel.getUserId(), userResponseModel.getUserId());
		assertEquals(userLocationResponseModel.getEmail(), "User1Email2@jitpay.com");
		assertEquals(userLocationResponseModel.getFirstName(), "User1 FirstName");
		assertEquals(userLocationResponseModel.getSecondName(), "User1 SecondName");
		assertNotNull(userLocationResponseModel.getLocation());
		assertNull(userLocationResponseModel.getLocation().getLongitude());
		assertNull(userLocationResponseModel.getLocation().getLatitude());
	}

	/**
	 * Test getLastLocationByUserId method through controller with one location
	 * @throws Exception
	 */
	@Test
	public void getWithOneLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email3@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+userResponseModel.getUserId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserLocationResponseModel userLocationResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserLocationResponseModel.class);

		assertEquals(userLocationResponseModel.getUserId(), userResponseModel.getUserId());
		assertEquals(userLocationResponseModel.getEmail(), "User1Email3@jitpay.com");
		assertEquals(userLocationResponseModel.getFirstName(), "User1 FirstName");
		assertEquals(userLocationResponseModel.getSecondName(), "User1 SecondName");
		assertNotNull(userLocationResponseModel.getLocation());
		assertNotNull(userLocationResponseModel.getLocation().getLongitude());
		assertNotNull(userLocationResponseModel.getLocation().getLatitude());
		assertEquals(userLocationResponseModel.getLocation().getLongitude(), -10);
		assertEquals(userLocationResponseModel.getLocation().getLatitude(), 10);
	}

	/**
	 * Test getLastLocationByUserId method through controller with multiple location
	 * @throws Exception
	 */
	@Test
	public void getWithMultipleLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email4@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel1 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel1);

		TimeUnit.SECONDS.sleep(1);

		UserLocationRequestModel userLocationRequestModel2 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10.1,(double)10.2));
		locationBusinessService.insert(userLocationRequestModel2);

		TimeUnit.SECONDS.sleep(1);

		UserLocationRequestModel userLocationRequestModel3 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)20.5,(double)30.4));
		locationBusinessService.insert(userLocationRequestModel3);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+userResponseModel.getUserId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserLocationResponseModel userLocationResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserLocationResponseModel.class);

		assertEquals(userLocationResponseModel.getUserId(), userResponseModel.getUserId());
		assertEquals(userLocationResponseModel.getEmail(), "User1Email4@jitpay.com");
		assertEquals(userLocationResponseModel.getFirstName(), "User1 FirstName");
		assertEquals(userLocationResponseModel.getSecondName(), "User1 SecondName");
		assertNotNull(userLocationResponseModel.getLocation());
		assertNotNull(userLocationResponseModel.getLocation().getLongitude());
		assertNotNull(userLocationResponseModel.getLocation().getLatitude());
		assertEquals(userLocationResponseModel.getLocation().getLongitude(), 20.5);
		assertEquals(userLocationResponseModel.getLocation().getLatitude(), 30.4);
	}
}
