package com.abbasaskari.test.jitpay.userapi.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A model for keeping user location response information
 */

public class UserLocationResponseModel extends BaseModel{

    @NotNull
    @Size(min = 1, max = 255)
    private String userId;

    @NotNull
    private Date createdOn;

    @NotNull
    @Size(min = 1, max = 255)
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    private String secondName;

    @NotNull
    private LocationModel location;

    public UserLocationResponseModel() {
    }

    public UserLocationResponseModel(@NotNull @Size(min = 1, max = 255) String userId, @NotNull Date createdOn, @NotNull @Size(min = 1, max = 255) String email, @NotNull @Size(min = 1, max = 255) String firstName, @NotNull @Size(min = 1, max = 255) String secondName, @NotNull Double longitude, @NotNull Double latitude) {
        this.userId = userId;
        this.createdOn = createdOn;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.location = new LocationModel(longitude, latitude);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}