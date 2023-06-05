package com.gs.infra;

public class ErrorMessage implements java.io.Serializable{
    public static final int NO_ERROR = 0;
    public static final int NO_KEEPALIVE_DATA = 1;
    public static final int SPACE_NOT_AVIALABEL = 2;
    public static final int DATA_NOT_FRESH = 3;
    public static final int INVALID_MISPAR_HESHBON = 4;
    public static final int FAIL_TO_RUN_USECASE = 5;

    Integer code;
    String Message;

    public ErrorMessage(Integer code, String message) {
        this.code = code;
        Message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code=" + code +
                ", Message='" + Message + '\'' +
                '}';
    }
}
