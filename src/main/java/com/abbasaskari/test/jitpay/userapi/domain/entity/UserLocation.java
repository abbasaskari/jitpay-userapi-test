package com.abbasaskari.test.jitpay.userapi.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * An entity for persistence Location information
 */

@Entity
@Table(name = "USER_LOCATION")
public class UserLocation extends BaseEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_LOCATION_SEQ")
    @SequenceGenerator(name = "USER_LOCATION_SEQ", sequenceName = "USER_LOCATION_SEQ")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_FK",referencedColumnName = "ID", nullable = false)
    private User user;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Basic(optional = false)
    @NotNull
    @Min(value = -180)
    @Max(value = 180)
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Basic(optional = false)
    @NotNull
    @Min(value = -90)
    @Max(value = 90)
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    public UserLocation() {
    }

    public UserLocation(@NotNull Long id, @NotNull User user, Date createdOn, @NotNull @Min(value = -180) @Max(value = 180) Double longitude, @NotNull @Min(value = -90) @Max(value = 90) Double latitude) {
        this.id = id;
        this.user = user;
        this.createdOn = createdOn;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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
