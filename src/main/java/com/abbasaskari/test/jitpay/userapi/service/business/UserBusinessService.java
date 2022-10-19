package com.abbasaskari.test.jitpay.userapi.service.business;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * An interface that represent User services
 */

public interface UserBusinessService {

    /**
     * Insert user information
     * @param userRequestModel
     * @return
     * @throws BaseException
     */
    UserResponseModel insert(UserRequestModel userRequestModel) throws BaseException;

    /**
     * Update user information
     * @param userId
     * @param userRequestModel
     * @throws BaseException
     */
    void update(String userId, UserRequestModel userRequestModel) throws BaseException;

}
