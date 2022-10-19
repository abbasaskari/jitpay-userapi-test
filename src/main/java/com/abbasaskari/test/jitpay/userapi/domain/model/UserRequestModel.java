package com.abbasaskari.test.jitpay.userapi.domain.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A model for keeping user request information
 */

public class UserRequestModel extends BaseModel {

    @NotNull(message = "Email is null.")
    @Size(message = "Email size is wrong.", min = 1, max = 255)
    @Email(message = "Email format  is wrong.")
    private String email;

    @NotNull(message = "First name is null.")
    @Size(message = "First name size is wrong.", min = 1, max = 255)
    private String firstName;

    @NotNull(message = "Second name is null.")
    @Size(message = "Second name size is wrong.", min = 1, max = 255)
    private String secondName;

    public UserRequestModel() {
    }

    public UserRequestModel(@NotNull @Size(min = 1, max = 255) String email, @NotNull @Size(min = 1, max = 255) String firstName, @NotNull @Size(min = 1, max = 255) String secondName) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
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
}
