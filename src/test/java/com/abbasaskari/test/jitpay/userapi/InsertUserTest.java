package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the insertUser method through BusinessService
 */
@SpringBootTest
class InsertUserTest {
	@Autowired
	private UserBusinessService userBusinessService;

	/**
	 * Test insertUser method through BusinessService
	 * @throws BaseException
	 */
	@Test
	public void insertUser() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email27@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		assertNotNull(userResponseModel.getUserId());
		assertNotNull(userResponseModel.getCreatedOn());
	}

	/**
	 * Test insertUser method through BusinessService with duplicate email
	 * @throws BaseException
	 */
	@Test
	public void insertUserWithDuplicateEmail() throws BaseException {
		UserRequestModel userRequestModel1 = new UserRequestModel("User1Email28@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel1 = userBusinessService.insert(userRequestModel1);

		UserRequestModel userRequestModel2 = new UserRequestModel("User1Email28@jitpay.com", "User2 FirstName", "User2 SecondName");

		Exception exception = assertThrows(BaseException.class, () -> {
			UserResponseModel userResponseModel2 = userBusinessService.insert(userRequestModel2);
		});

		String expectedMessage = "Email is duplicate.";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

}
