package com.gs.infra.service;

public class ErrorServiceResponse implements ServiceResponse{

    String title;
    String status;
    String detail;

    public ErrorServiceResponse(String title, String status, String detail){
        this.title = title;
        this.status = status;
        this.detail = detail;
    }
}
