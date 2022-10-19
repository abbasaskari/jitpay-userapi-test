package com.abbasaskari.test.jitpay.userapi.service.mapper;

import com.abbasaskari.test.jitpay.userapi.domain.entity.User;
import com.abbasaskari.test.jitpay.userapi.domain.entity.UserLocation;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationResponseModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationsResponseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class converts Location Models and Entities together
 */
public class LocationMapper{

    /**
     * Map UserLocation entity to UserLocationResponseModel
     * @param userLocation
     * @return
     */
    public UserLocationResponseModel userLocationToUserLocationResponseModel(UserLocation userLocation)
    {
        if (userLocation == null)
            return null;
        return new UserLocationResponseModel(userLocation.getUser().getUserId(), userLocation.getUser().getCreatedOn(), userLocation.getUser().getEmail(), userLocation.getUser().getFirstName(), userLocation.getUser().getSecondName(), userLocation.getLongitude(), userLocation.getLatitude());
    }

    /**
     * Map User entity to UserLocationResponseModel
     * @param user
     * @return
     */
    public UserLocationResponseModel userToUserLocationResponseModel(User user)
    {
        if (user == null)
            return null;
        return new UserLocationResponseModel(user.getUserId(), user.getCreatedOn(), user.getEmail(), user.getFirstName(), user.getSecondName(), null, null);
    }

    /**
     * Map UserLocation List to UserLocationsResponseModel
     * @param userLocations
     * @return
     */
    public UserLocationsResponseModel userLocationListToUserLocationsResponseModel(List<UserLocation> userLocations)
    {
        if (userLocations == null || userLocations.size() == 0)
            return null;

        UserLocationsResponseModel userLocationsResponseModel = new UserLocationsResponseModel();
        userLocationsResponseModel.setUserId(userLocations.get(0).getUser().getUserId());
        for (UserLocation userLocation : userLocations)
        {
            UserLocationsResponseModel.LocationsModel locationsModel = new UserLocationsResponseModel.LocationsModel(userLocation.getCreatedOn(), userLocation.getLongitude(), userLocation.getLatitude());
            userLocationsResponseModel.getLocations().add(locationsModel);
        }
        return userLocationsResponseModel;
    }

    /**
     * Map User entity to UserLocationsResponseModel
     * @param user
     * @return
     */
    public UserLocationsResponseModel userToUserLocationsResponseModel(User user)
    {
        if (user == null)
            return null;

        return new UserLocationsResponseModel(user.getUserId(), new ArrayList<>());
    }

    /**
     * Map UserLocationRequestModel and User entity to UserLocation entity
     * @param userLocationRequestModel
     * @param user
     * @return
     */
    public UserLocation userLocationRequestModelToUserLocation(UserLocationRequestModel userLocationRequestModel, User user)
    {
        if (userLocationRequestModel == null)
            return null;
        return new UserLocation(null, user, userLocationRequestModel.getCreatedOn(),userLocationRequestModel.getLocation().getLongitude(), userLocationRequestModel.getLocation().getLatitude());
    }
}
