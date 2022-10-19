package com.abbasaskari.test.jitpay.userapi.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A model for keeping user response information
 */

public class UserResponseModel extends BaseModel {

    @NotNull
    @Size(min = 1, max = 255)
    private String userId;

    @NotNull
    private Date createdOn;

    public UserResponseModel() {
    }

    public UserResponseModel(@NotNull @Size(min = 1, max = 255) String userId, @NotNull Date createdOn) {
        this.userId = userId;
        this.createdOn = createdOn;
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
}
