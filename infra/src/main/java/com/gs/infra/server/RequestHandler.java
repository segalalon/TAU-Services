package com.gs.infra.server;

import com.google.gson.JsonObject;
import com.gs.infra.health.HealthReporter;
import com.gs.infra.health.HealthStatus;
import com.gs.infra.metadata.ServiceCatalog;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import javax.annotation.Resource;

public abstract class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Resource
    protected GigaSpace gigaSpace;

    protected HealthReporter healthReporter;
    protected ServiceCatalog serviceCatalog;


    public RequestHandler(GigaSpace space, HealthReporter healthReporter, ServiceCatalog serviceCatalog) {
        this.gigaSpace = space;
        this.healthReporter = healthReporter;
        this.serviceCatalog = serviceCatalog;
    }

    public Route health = ((request, response) -> {
        int num = healthReporter.isHealthy();
        logger.debug("HEALTH returned {}", num);
        if (num != 200){
            response.status(500);
            return HealthStatus.code.FAILED;
        }
        response.status(200);
        return HealthStatus.code.PASSED;
    });

    public Route metadata = ((request, response) -> {
        JsonObject json = serviceCatalog.getMetaData();
        logger.debug("METADATA returned {}", json
        );

        return json;
    });

    public GigaSpace getGigaSpace(){
        return gigaSpace;
    }

    public HealthReporter getHealthReporter() {
        return healthReporter;
    }

    public void setHealthReporter(HealthReporter healthReporter) {
        this.healthReporter = healthReporter;
    }
}

