package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.domain.model.*;
import com.abbasaskari.test.jitpay.userapi.service.business.LocationBusinessService;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the getLocationsByDateTimeRange method through BusinessService
 */
@SpringBootTest
class GetLocationsByDateTimeRangeTest {
	@Autowired
	private UserBusinessService userBusinessService;
	@Autowired
	private LocationBusinessService locationBusinessService;

	/**
	 * Test getLocationsByDateTimeRange method through BusinessService with fake user
	 * @throws BaseException
	 */
	@Test
	public void getWithFakeUser() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email13@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
		Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

		Exception exception = assertThrows(BaseException.class, () -> {
			UserLocationsResponseModel userLocationsResponseModel = locationBusinessService.getLocationsByDateTimeRange("Test 1", yesterday, tomorrow);
		});
		String expectedMessage = "User not found.";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test getLocationsByDateTimeRange method through BusinessService without location
	 * @throws BaseException
	 */
	@Test
	public void getWithoutLocation() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email14@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
		Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

		UserLocationsResponseModel userLocationsResponseModel = locationBusinessService.getLocationsByDateTimeRange(userResponseModel.getUserId(), yesterday, tomorrow);

		assertEquals(userLocationsResponseModel.getUserId(), userResponseModel.getUserId());
		assertNotNull(userLocationsResponseModel.getLocations());
		assertEquals(userLocationsResponseModel.getLocations().size(), 0);
	}

	/**
	 * Test getLocationsByDateTimeRange method through BusinessService with one location
	 * @throws BaseException
	 * @throws InterruptedException
	 */
	@Test
	public void getWithOneLocation() throws BaseException, InterruptedException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email15@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel);

		TimeUnit.SECONDS.sleep(1);

		Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
		Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

		UserLocationsResponseModel userLocationsResponseModel = locationBusinessService.getLocationsByDateTimeRange(userResponseModel.getUserId(), yesterday, tomorrow);

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
	 * Test getLocationsByDateTimeRange method through BusinessService with multiple location
	 * @throws BaseException
	 * @throws InterruptedException
	 */
	@Test
	public void getWithMultipleLocation() throws BaseException, InterruptedException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email16@jitpay.com", "User1 FirstName", "User1 SecondName");
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

		Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
		Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

		UserLocationsResponseModel userLocationsResponseModel = locationBusinessService.getLocationsByDateTimeRange(userResponseModel.getUserId(), yesterday, tomorrow);

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
