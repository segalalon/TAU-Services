package com.gs.infra;

public class ServiceException extends RuntimeException {

    public ServiceException(String message, int code, Throwable cause) {
        super("ErrorMessage{ code=" + code + ", Message='" + message + "'}", cause);
    }
}
