package com.abbasaskari.test.jitpay.userapi.common.exception;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class is used for exception handling
 */
public class BaseException extends Exception {
    private String message;
    private Object[] params;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BaseException(String message, Object[] params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public BaseException(String message, Object[] params, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.params = params;
    }

    public String getMessage() {
        return this.message;
    }

    public Object[] getParams() {
        return this.params;
    }
}
