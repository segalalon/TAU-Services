/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gs.infra.server;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.gs.infra.health.HealthReporter;
import com.gs.infra.metadata.ServiceCatalog;
import com.gs.infra.service.ServiceRoute;
import com.gs.lib.grid.RefTableCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;


//@Component
public abstract class ServerMapping extends ApiServer {
    private static final Logger logger = LoggerFactory.getLogger(ServerMapping.class);

    protected RefTableCache refTableCache;
    protected RefTableCache refDocTableCache;
    private final BaseHandler baseHandler;


    public ServerMapping(int minPort, int maxPort, String serviceId, String requiredRole, String consulHost,
                         int consulPort, HealthReporter healthReporter, String version, RequestHandler requestHandler, ServiceCatalog serviceCatalog) {
        super (minPort, maxPort, serviceId, requiredRole, consulHost, consulPort, healthReporter, version, requestHandler, serviceCatalog);
        baseHandler = (BaseHandler) requestHandler;
        refTableCache = null;
        refDocTableCache = null;
    }

    public abstract String getEndpoint();

    public abstract String generateSwagger();


        public abstract ServiceRoute getServiceRoute();

//    @Override
//    public void definePaths() {
//        final Gson gson = new Gson();
//        path("/" + version, () -> {
//            before("/*", (q, a) -> logger.info("Received api call"));
//
//            get("/route", new ServiceRoute(gigaSpace, new Nt2crJdbcTask(), new Nt2crBinder()), gson::toJson);
//            get("/route2", new ServiceRoute(gigaSpace, new Nt2crJdbcTask(), new Nt2crBinder(), true), gson::toJson);
//            get("/route3", new ServiceRoute(gigaSpace, new Nt2crApiTask(), new Nt2crBinder()), gson::toJson);
//            get("/route4", new ServiceRoute(gigaSpace, new Nt2crApiTask(), new Nt2crBinder(), true), gson::toJson);
//            get("/DemoExample", baseHandler.demo, gson::toJson);
//        });
//
//    }

    @Override
    public void definePaths() throws JsonProcessingException {
        final String swaggerJson = generateSwagger();
        final Gson gson = new Gson();
        path("/" + version, () -> {
            before("/*", (q, a) -> logger.info("Received api call " +  getEndpoint()));
            get(getEndpoint(), getServiceRoute().setGigaspaces(gigaSpace).setRefTableCache(refTableCache, refDocTableCache), gson::toJson);
            get("/swagger", ((request, response) -> {return swaggerJson;}));
        });
    }
}
