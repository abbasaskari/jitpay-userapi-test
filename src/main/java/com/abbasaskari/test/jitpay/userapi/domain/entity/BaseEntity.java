package com.abbasaskari.test.jitpay.userapi.domain.entity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class is a base entity that every entity inheritance this
 * This can be used for persistence Time and Authority
 */

@MappedSuperclass
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}
