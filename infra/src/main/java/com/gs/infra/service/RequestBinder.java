package com.gs.infra.service;

public interface RequestBinder{

    ServiceRequest bind(spark.Request request);
}
