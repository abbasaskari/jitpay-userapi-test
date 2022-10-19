package com.abbasaskari.test.jitpay.userapi.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * An entity for persistence User information
 */

@Entity
@Table(name = "USER",
        indexes = {
            @Index(name = "USER_IDX_1", columnList = "USER_ID", unique = true)
        },
        uniqueConstraints = {
            @UniqueConstraint(name = "USER_UNQ_1", columnNames = {"USER_ID"}),
            @UniqueConstraint(name = "USER_UNQ_2", columnNames = {"EMAIL"})
        }
)
public class User extends BaseEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USER_ID", nullable = false, length = 255, unique = true)
    private String userId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EMAIL", nullable = false, length = 255, unique = true)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FIRST_NAME", nullable = false, length = 255)
    private String firstName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SECOND_NAME", nullable = false, length = 255)
    private String secondName;

    public User() {
    }

    public User(@NotNull Long id, @NotNull @Size(min = 1, max = 255) String userId, Date createdOn, @NotNull @Size(min = 1, max = 255) String email, @NotNull @Size(min = 1, max = 255) String firstName, @NotNull @Size(min = 1, max = 255) String secondName) {
        this.id = id;
        this.userId = userId;
        this.createdOn = createdOn;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
