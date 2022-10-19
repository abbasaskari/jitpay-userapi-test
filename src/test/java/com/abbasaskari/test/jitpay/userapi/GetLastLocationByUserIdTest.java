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
 * This class contains several tests to test the getLastLocationByUserId method through BusinessService
 */
@SpringBootTest
class GetLastLocationByUserIdTest {
	@Autowired
	private UserBusinessService userBusinessService;
	@Autowired
	private LocationBusinessService locationBusinessService;

	/**
	 * Test getLastLocationByUserId method through BusinessService with fake user
	 * @throws BaseException
	 */
	@Test
	public void getWithFakeUser() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email5@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		Exception exception = assertThrows(BaseException.class, () -> {
			UserLocationResponseModel userLocationResponseModel = locationBusinessService.getLastLocationByUserId("Test 1");
		});
		String expectedMessage = "User not found.";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test getLastLocationByUserId method through BusinessService without location
	 * @throws BaseException
	 */
	@Test
	public void getWithoutLocation() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email6@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationResponseModel userLocationResponseModel = locationBusinessService.getLastLocationByUserId(userResponseModel.getUserId());

		assertEquals(userLocationResponseModel.getUserId(), userResponseModel.getUserId());
		assertEquals(userLocationResponseModel.getEmail(), "User1Email6@jitpay.com");
		assertEquals(userLocationResponseModel.getFirstName(), "User1 FirstName");
		assertEquals(userLocationResponseModel.getSecondName(), "User1 SecondName");
		assertNotNull(userLocationResponseModel.getLocation());
		assertNull(userLocationResponseModel.getLocation().getLongitude());
		assertNull(userLocationResponseModel.getLocation().getLatitude());
	}

	/**
	 * Test getLastLocationByUserId method through BusinessService with one location
	 * @throws BaseException
	 */
	@Test
	public void getWithOneLocation() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email7@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel);

		UserLocationResponseModel userLocationResponseModel = locationBusinessService.getLastLocationByUserId(userResponseModel.getUserId());

		assertEquals(userLocationResponseModel.getUserId(), userResponseModel.getUserId());
		assertEquals(userLocationResponseModel.getEmail(), "User1Email7@jitpay.com");
		assertEquals(userLocationResponseModel.getFirstName(), "User1 FirstName");
		assertEquals(userLocationResponseModel.getSecondName(), "User1 SecondName");
		assertNotNull(userLocationResponseModel.getLocation());
		assertNotNull(userLocationResponseModel.getLocation().getLongitude());
		assertNotNull(userLocationResponseModel.getLocation().getLatitude());
		assertEquals(userLocationResponseModel.getLocation().getLongitude(), -10);
		assertEquals(userLocationResponseModel.getLocation().getLatitude(), 10);
	}

	/**
	 * Test getLastLocationByUserId method through BusinessService with multiple location
	 * @throws BaseException
	 * @throws InterruptedException
	 */
	@Test
	public void getWithMultipleLocation() throws BaseException, InterruptedException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email8@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel1 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10,(double)10));
		locationBusinessService.insert(userLocationRequestModel1);

		TimeUnit.SECONDS.sleep(1);

		UserLocationRequestModel userLocationRequestModel2 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)-10.1,(double)10.2));
		locationBusinessService.insert(userLocationRequestModel2);

		TimeUnit.SECONDS.sleep(1);

		UserLocationRequestModel userLocationRequestModel3 = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel((double)20.5,(double)30.4));
		locationBusinessService.insert(userLocationRequestModel3);

		UserLocationResponseModel userLocationResponseModel = locationBusinessService.getLastLocationByUserId(userResponseModel.getUserId());

		assertEquals(userLocationResponseModel.getUserId(), userResponseModel.getUserId());
		assertEquals(userLocationResponseModel.getEmail(), "User1Email8@jitpay.com");
		assertEquals(userLocationResponseModel.getFirstName(), "User1 FirstName");
		assertEquals(userLocationResponseModel.getSecondName(), "User1 SecondName");
		assertNotNull(userLocationResponseModel.getLocation());
		assertNotNull(userLocationResponseModel.getLocation().getLongitude());
		assertNotNull(userLocationResponseModel.getLocation().getLatitude());
		assertEquals(userLocationResponseModel.getLocation().getLongitude(), 20.5);
		assertEquals(userLocationResponseModel.getLocation().getLatitude(), 30.4);
	}
}
