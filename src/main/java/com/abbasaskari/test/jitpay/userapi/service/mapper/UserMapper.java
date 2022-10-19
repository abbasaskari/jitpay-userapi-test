package com.abbasaskari.test.jitpay.userapi.service.mapper;

import com.abbasaskari.test.jitpay.userapi.domain.entity.User;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class converts User Models and Entities together
 */
public class UserMapper {

    /**
     * Map UserRequestModel to User entity
     * @param userRequestModel
     * @return
     */
    public User userRequestModelToUser(UserRequestModel userRequestModel)
    {
        return new User(null, null, null, userRequestModel.getEmail(), userRequestModel.getFirstName(), userRequestModel.getSecondName());
    }

    /**
     * Map UserRequestModel and User entity to User entity
     * @param userRequestModel
     * @param user
     * @return
     */
    public User userRequestModelToUser(UserRequestModel userRequestModel, User user){
        return new User(user.getId(), user.getUserId(), user.getCreatedOn(), userRequestModel.getEmail(), userRequestModel.getFirstName(), userRequestModel.getSecondName());
    }

    /**
     * Map User entity to UserResponseModel
     * @param user
     * @return
     */
    public UserResponseModel userToUserResponseModel(User user){
        return new UserResponseModel(user.getUserId(), user.getCreatedOn());
    }
}
