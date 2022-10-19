package com.abbasaskari.test.jitpay.userapi.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A model for keeping user locations response information
 * Include LocationsModel subclass
 */

public class UserLocationsResponseModel extends BaseModel {


    @NotNull
    @Size(min = 1, max = 255)
    private String userId;

    private List<LocationsModel> locations = new ArrayList<>();

    public UserLocationsResponseModel() {
    }

    public UserLocationsResponseModel(@NotNull @Size(min = 1, max = 255) String userId, List<LocationsModel> locations) {
        this.userId = userId;
        this.locations = locations;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<LocationsModel> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationsModel> locations) {
        this.locations = locations;
    }

    public static class LocationsModel extends BaseModel {

        @NotNull
        private Date createdOn;

        @NotNull
        private LocationModel location;

        public LocationsModel() {
        }

        public LocationsModel(@NotNull Date createdOn, @NotNull LocationModel location) {
            this.createdOn = createdOn;
            this.location = location;
        }

        public LocationsModel(@NotNull Date createdOn, @NotNull Double longitude, @NotNull Double latitude) {
            this.createdOn = createdOn;
            this.location = new LocationModel(longitude, latitude);
        }

        public Date getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }

        public LocationModel getLocation() {
            return location;
        }

        public void setLocation(LocationModel location) {
            this.location = location;
        }
    }
}