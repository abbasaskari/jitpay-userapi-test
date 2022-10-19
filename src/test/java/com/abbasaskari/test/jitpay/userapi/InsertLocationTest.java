package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.domain.model.LocationModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.business.LocationBusinessService;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the insertLocation method through BusinessService
 */
@SpringBootTest
class InsertLocationTest {
	@Autowired
	private UserBusinessService userBusinessService;
	@Autowired
	private LocationBusinessService locationBusinessService;

	/**
	 * Test insertLocation method through controller
	 * @throws BaseException
	 */
	@Test
	public void insertLocation() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email19@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel(userResponseModel.getUserId(), new Date(), new LocationModel(-10.1,10.2));
		locationBusinessService.insert(userLocationRequestModel);

		assert(true);
	}

	/**
	 * Test insertLocation method through controller with fake user
	 * @throws BaseException
	 */
	@Test
	public void insertULocationWithFakeUser() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email20@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserLocationRequestModel userLocationRequestModel = new UserLocationRequestModel("Test 1", new Date(), new LocationModel((double)-10,(double)10));

		Exception exception = assertThrows(BaseException.class, () -> {
			locationBusinessService.insert(userLocationRequestModel);
		});

		String expectedMessage = "User not found.";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}
}
