package com.gs.infra.server;

import com.gs.infra.health.HealthReporter;
import com.gs.infra.metadata.ServiceCatalog;
import org.openspaces.core.GigaSpace;
import spark.Route;

public class BaseHandler extends RequestHandler {


    public BaseHandler(GigaSpace space, HealthReporter healthReporter, ServiceCatalog serviceCatalog) {
        super(space, healthReporter, serviceCatalog);
    }

    public Route demo = ((request, response) -> {
        SecurityUtils.setpassphrase();
        return "DONE";
    });

}

