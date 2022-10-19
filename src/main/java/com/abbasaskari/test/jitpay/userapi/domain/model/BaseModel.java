package com.abbasaskari.test.jitpay.userapi.domain.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class is a base model that every model inheritance this
 * This can be used for keeping Time, Authority and Versioning
 */

public abstract class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}
