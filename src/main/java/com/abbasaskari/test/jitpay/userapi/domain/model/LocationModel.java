package com.abbasaskari.test.jitpay.userapi.domain.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A model for keeping Location information consist of longitude, latitude
 */

public class LocationModel extends BaseModel {
    @NotNull(message = "Longitude is null.")
    @Min(message = "Longitude is less than -180", value = -180)
    @Max(message = "Longitude is more than 180", value = 180)
    private Double longitude;

    @NotNull(message = "Latitude is null.")
    @Min(message = "Latitude is less than -90", value = -90)
    @Max(message = "Latitude is more than 90", value = 90)
    private Double latitude;

    public LocationModel() {
    }

    public LocationModel(@NotNull @Min(value = -180) @Max(value = 180) Double longitude, @NotNull @Min(value = -90) @Max(value = 90) Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
