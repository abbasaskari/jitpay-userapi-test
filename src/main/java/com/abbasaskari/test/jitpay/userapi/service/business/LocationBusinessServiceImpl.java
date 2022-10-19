package com.abbasaskari.test.jitpay.userapi.service.business;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.common.log.LogUtil;
import com.abbasaskari.test.jitpay.userapi.domain.entity.User;
import com.abbasaskari.test.jitpay.userapi.domain.entity.UserLocation;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationResponseModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationsResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.dao.LocationDao;
import com.abbasaskari.test.jitpay.userapi.service.dao.UserDao;
import com.abbasaskari.test.jitpay.userapi.service.mapper.LocationMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * Implementation of LocationBusinessService interface that represent Location services
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class LocationBusinessServiceImpl implements LocationBusinessService {
    private final LocationDao locationDao;
    private final UserDao userDao;

    private static final Logger LOG = LogUtil.getDefaultLogger(LocationBusinessServiceImpl.class);

    @Autowired
    public LocationBusinessServiceImpl(LocationDao locationDao, UserDao userDao) {
        this.locationDao = locationDao;
        this.userDao = userDao;
    }

    @Override
    public void insert(UserLocationRequestModel userLocationRequestModel) throws BaseException {
        LOG.info("insert start");

        User user = userDao.findByUserId(userLocationRequestModel.getUserId());

        if (user == null)
            throw new BaseException("User not found.");

        LocationMapper locationMapper = new LocationMapper();
        UserLocation userLocation = locationMapper.userLocationRequestModelToUserLocation(userLocationRequestModel, user);

        locationDao.save(userLocation);
    }

    @Override
    public UserLocationResponseModel getLastLocationByUserId(String userId) throws BaseException
    {
        LOG.info("getLastLocationByUserId start");

        User user = userDao.findByUserId(userId);

        if (user == null)
            throw new BaseException("User not found.");

        UserLocation userLocation = locationDao.findLatestLocationByUserId(userId);

        LocationMapper locationMapper = new LocationMapper();
        UserLocationResponseModel userLocationResponseModel = new UserLocationResponseModel();
        if (userLocation == null)
            userLocationResponseModel = locationMapper.userToUserLocationResponseModel(user);
        else
            userLocationResponseModel = locationMapper.userLocationToUserLocationResponseModel(userLocation);

        return userLocationResponseModel;
    }

    @Override
    public UserLocationsResponseModel getLocationsByDateTimeRange(String userId, Date start, Date end) throws BaseException
    {
        LOG.info("getLocationsByDateTimeRange start");

        User user = userDao.findByUserId(userId);

        if (user == null)
            throw new BaseException("User not found.");

        List<UserLocation> userLocations = locationDao.findByUserIdAndDateTimeRange(userId, start, end);

        LocationMapper locationMapper = new LocationMapper();
        UserLocationsResponseModel userLocationsResponseModel = new UserLocationsResponseModel();
        if (userLocations.size() == 0)
            userLocationsResponseModel = locationMapper.userToUserLocationsResponseModel(user);
        else
            userLocationsResponseModel = locationMapper.userLocationListToUserLocationsResponseModel(userLocations);

        return userLocationsResponseModel;
    }
}
