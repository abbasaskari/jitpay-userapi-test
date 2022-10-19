package com.abbasaskari.test.jitpay.userapi.domain.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A model for keeping user location request information
 */

public class UserLocationRequestModel extends BaseModel {

    @NotNull(message = "User id is null.")
    private String userId;

    @NotNull(message = "Created on is null.")
    @DateTimeFormat
    private Date createdOn;

    @NotNull(message = "Location is null.")
    @Valid
    private LocationModel location;

    public UserLocationRequestModel() {
    }

    public UserLocationRequestModel(@NotNull String userId, @NotNull Date createdOn, @NotNull LocationModel location) {
        this.userId = userId;
        this.createdOn = createdOn;
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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