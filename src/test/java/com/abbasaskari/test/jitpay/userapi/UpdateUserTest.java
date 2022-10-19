package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.domain.entity.User;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import com.abbasaskari.test.jitpay.userapi.service.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class contains several tests to test the updateUser method through BusinessService
 */
@SpringBootTest
class UpdateUserTest {
	@Autowired
	private UserBusinessService userBusinessService;
	@Autowired
	private UserDao userDao;

	/**
	 * Test updateUser method through BusinessService
	 * @throws BaseException
	 */
	@Test
	void updateUser() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email39@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserRequestModel userRequestMode2 = new UserRequestModel("UpdatedUser1Email39@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");
		userBusinessService.update(userResponseModel.getUserId(), userRequestMode2);

		assert(true);
	}

	/**
	 * Test updateUser method through BusinessService and then check values
	 * @throws BaseException
	 */
	@Test
	public void updateUserCheckValues() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email30@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserRequestModel userRequestMode2 = new UserRequestModel("UpdatedUser1Email30@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");
		userBusinessService.update(userResponseModel.getUserId(), userRequestMode2);

		User user = userDao.findByUserId(userResponseModel.getUserId());

		assertEquals(user.getEmail(), userRequestMode2.getEmail());
		assertEquals(user.getFirstName(), userRequestMode2.getFirstName());
		assertEquals(user.getSecondName(), userRequestMode2.getSecondName());
	}

	/**
	 * Test updateUser method through BusinessService with fake user
	 * @throws BaseException
	 */
	@Test
	public void updateUserWithFakeUserId() throws BaseException {
		UserRequestModel userRequestModel1 = new UserRequestModel("User1email40@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel1 = userBusinessService.insert(userRequestModel1);

		UserRequestModel userRequestMode2 = new UserRequestModel("UpdatedUser1Email40@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");

		Exception exception = assertThrows(BaseException.class, () -> {
			userBusinessService.update("Test 1", userRequestMode2);
		});

		String expectedMessage = "User not found.";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through BusinessService with duplicate email
	 * @throws BaseException
	 */
	@Test
	public void updateUserWithDuplicateEmail() throws BaseException {
		UserRequestModel userRequestModel1 = new UserRequestModel("User1email41@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel1 = userBusinessService.insert(userRequestModel1);

		UserRequestModel userRequestModel2 = new UserRequestModel("User1emai42@jitpay.com", "User2 FirstName", "User2 SecondName");
		UserResponseModel userResponseModel2 = userBusinessService.insert(userRequestModel2);

		UserRequestModel userRequestModel3 = new UserRequestModel(userRequestModel2.getEmail(), "Updated User1 FirstName", "Updated User1 SecondName");
		Exception exception = assertThrows(BaseException.class, () -> {
			userBusinessService.update(userResponseModel1.getUserId(), userRequestModel3);
		});

		String expectedMessage = "Email is duplicate.";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	/**
	 * Test updateUser method through BusinessService with same email
	 * @throws BaseException
	 */
	@Test
	public void updateUserWithSameEmail() throws BaseException {
		UserRequestModel userRequestModel = new UserRequestModel("User1Email43@jitpay.com", "User1 FirstName", "User1 SecondName");
		UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);

		UserRequestModel userRequestMode2 = new UserRequestModel("User1Email43@jitpay.com", "Updated User1 FirstName", "Updated User1 SecondName");
		userBusinessService.update(userResponseModel.getUserId(), userRequestMode2);

		assert(true);
	}
}
