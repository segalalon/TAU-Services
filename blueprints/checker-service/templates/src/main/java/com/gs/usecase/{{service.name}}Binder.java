package com.gs.usecase;

import com.google.gson.Gson;
import com.gs.infra.service.RequestBinder;
import com.gs.infra.service.ServiceRequest;

/**
 * Binds a general request to the specific service.
 */
public class {{service.name}}Binder implements RequestBinder {

    private final Gson gson = new Gson();

    @Override
    public ServiceRequest bind(spark.Request request) {
        return gson.fromJson(request.body(), {{service.name}}Request.class);
    }
}
