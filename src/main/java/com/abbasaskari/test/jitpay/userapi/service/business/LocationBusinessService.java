package com.abbasaskari.test.jitpay.userapi.service.business;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationResponseModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationsResponseModel;

import java.util.Date;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * An interface that represent Location services
 */

public interface LocationBusinessService {

    /**
     * Insert locations for user
     * @param userLocationRequestModel
     * @throws BaseException
     */
    void insert(UserLocationRequestModel userLocationRequestModel) throws BaseException;

    /**
     * It responds latest location for user by getting userId
     * @param userId
     * @return
     * @throws BaseException
     */
    UserLocationResponseModel getLastLocationByUserId(String userId) throws BaseException;

    /**
     * It responds user locations between two time by getting userId and time range
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws BaseException
     */
    UserLocationsResponseModel getLocationsByDateTimeRange(String userId, Date start, Date end) throws BaseException;

}
