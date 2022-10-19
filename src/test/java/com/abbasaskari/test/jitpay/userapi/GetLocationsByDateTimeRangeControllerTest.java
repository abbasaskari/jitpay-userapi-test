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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the getLocationsByDateTimeRange method through controller
 */
@SpringBootTest
class GetLocationsByDateTimeRangeControllerTest {
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
	private static final String uri = "/api/location/get-locations-by-date-time-range";

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
	 * Test getLocationsByDateTimeRange method through controller with fake user
	 * @throws Exception
	 */
	@Test
	public void getWithFakeUser() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email9@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

		String now = simpleDateFormat.format(new Date());
		String yesterday =  simpleDateFormat.format(new Date(System.currentTimeMillis()-24*60*60*1000));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+"Test 1"+"&startTime="+yesterday+"&endTime="+now)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(500, mvcResult.getResponse().getStatus());

		String expectedMessage = "User not found.";
		String actualMessage = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test getLocationsByDateTimeRange method through controller without location
	 * @throws Exception
	 */
	@Test
	public void getWithoutLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email10@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

		String now = simpleDateFormat.format(new Date());
		String yesterday =  simpleDateFormat.format(new Date(System.currentTimeMillis()-24*60*60*1000));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+userResponseModel.getUserId()+"&startTime="+yesterday+"&endTime="+now)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserLocationsResponseModel userLocationsResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserLocationsResponseModel.class);

		assertEquals(userLocationsResponseModel.getUserId(), userResponseModel.getUserId());
		assertNotNull(userLocationsResponseModel.getLocations());
		assertEquals(userLocationsResponseModel.getLocations().size(), 0);
	}

	/**
	 * Test getLocationsByDateTimeRange method through controller with one location
	 * @throws Exception
	 */
	@Test
	public void getWithOneLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email11@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel);

		TimeUnit.SECONDS.sleep(1);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

		String tomorrow = simpleDateFormat.format(new Date(System.currentTimeMillis()+24*60*60*1000));
		String yesterday =  simpleDateFormat.format(new Date(System.currentTimeMillis()-24*60*60*1000));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+userResponseModel.getUserId()+"&startTime="+yesterday+"&endTime="+tomorrow)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserLocationsResponseModel userLocationsResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserLocationsResponseModel.class);

		assertEquals(userLocationsResponseModel.getUserId(), userResponseModel.getUserId());
		assertNotNull(userLocationsResponseModel.getLocations());
		assertEquals(userLocationsResponseModel.getLocations().size(), 1);
		assertNotNull(userLocationsResponseModel.getLocations().get(0).getLocation());
		assertNotNull(userLocationsResponseModel.getLocations().get(0).getLocation().getLongitude());
		assertNotNull(userLocationsResponseModel.getLocations().get(0).getLocation().getLatitude());
		assertEquals(userLocationsResponseModel.getLocations().get(0).getLocation().getLongitude(), -10);
		assertEquals(userLocationsResponseModel.getLocations().get(0).getLocation().getLatitude(), 10);
	}

	/**
	 * Test getLocationsByDateTimeRange method through controller with multiple location
	 * @throws Exception
	 */
	@Test
	public void getWithMultipleLocation() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		UserRequestModel userRequestModel = new UserRequestModel("User1Email12@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel1 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel1);

		TimeUnit.SECONDS.sleep(1);

		UserLocationRequestModel userLocationRequestModel2 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10.1,(double)10.2));
		locationBusinessService.insert(userLocationRequestModel2);

		TimeUnit.SECONDS.sleep(1);

		UserLocationRequestModel userLocationRequestModel3 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)20.5,(double)30.4));
		locationBusinessService.insert(userLocationRequestModel3);

		TimeUnit.SECONDS.sleep(1);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

		String tomorrow = simpleDateFormat.format(new Date(System.currentTimeMillis()+24*60*60*1000));
		String yesterday =  simpleDateFormat.format(new Date(System.currentTimeMillis()-24*60*60*1000));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+ "?userId="+userResponseModel.getUserId()+"&startTime="+yesterday+"&endTime="+tomorrow)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		UserLocationsResponseModel userLocationsResponseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), UserLocationsResponseModel.class);

		assertEquals(userLocationsResponseModel.getUserId(), userResponseModel.getUserId());
		assertNotNull(userLocationsResponseModel.getLocations());
		assertEquals(userLocationsResponseModel.getLocations().size(), 3);

		assertNotNull(userLocationsResponseModel.getLocations().get(0).getLocation());
		assertNotNull(userLocationsResponseModel.getLocations().get(0).getLocation().getLongitude());
		assertNotNull(userLocationsResponseModel.getLocations().get(0).getLocation().getLatitude());
		assertEquals(userLocationsResponseModel.getLocations().get(0).getLocation().getLongitude(), -10);
		assertEquals(userLocationsResponseModel.getLocations().get(0).getLocation().getLatitude(), 10);

		assertNotNull(userLocationsResponseModel.getLocations().get(1).getLocation());
		assertNotNull(userLocationsResponseModel.getLocations().get(1).getLocation().getLongitude());
		assertNotNull(userLocationsResponseModel.getLocations().get(1).getLocation().getLatitude());
		assertEquals(userLocationsResponseModel.getLocations().get(1).getLocation().getLongitude(), -10.1);
		assertEquals(userLocationsResponseModel.getLocations().get(1).getLocation().getLatitude(), 10.2);

		assertNotNull(userLocationsResponseModel.getLocations().get(2).getLocation());
		assertNotNull(userLocationsResponseModel.getLocations().get(2).getLocation().getLongitude());
		assertNotNull(userLocationsResponseModel.getLocations().get(2).getLocation().getLatitude());
		assertEquals(userLocationsResponseModel.getLocations().get(2).getLocation().getLongitude(), 20.5);
		assertEquals(userLocationsResponseModel.getLocations().get(2).getLocation().getLatitude(), 30.4);
	}
}
