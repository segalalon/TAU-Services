package com.gs.infra.service;

import tau.ods.gs.model.logging.LogBuilder;
import com.gigaspaces.metrics.BeanMetricManager;
import com.gigaspaces.metrics.Gauge;
import com.gs.infra.ErrorMessage;
import com.gs.infra.ServiceException;
import com.gs.infra.health.HealthReporter;
import com.gs.lib.grid.RefTableCache;
import org.openspaces.core.GigaSpace;
import org.openspaces.pu.container.ProcessingUnitContainerContext;
import org.openspaces.pu.container.ProcessingUnitContainerContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ServiceRoute implements Route, ProcessingUnitContainerContextAware {
    private static final Logger logger = LoggerFactory.getLogger(HealthReporter.class);
    private RefTableCache refTableCache;
    private RefTableCache refDocTableCache;
    GeneralTask<ServiceRequest, ServiceResponse> service;

    RequestBinder requestBinder;

    GigaSpace gigaSpace;

    private BeanMetricManager beanMetricManager;

    private final AtomicInteger processedRequests = new AtomicInteger();
    private long lastLatency = 0;
    private int lastResultsCount = 0;


    boolean inPartition;

    public ServiceRoute() {
    }

    public void setService(GeneralTask service) {
        this.service = service;
    }

    public void setRequestBinder(RequestBinder requestBinder) {
        this.requestBinder = requestBinder;
    }

    public void setInPartition(boolean inPartition) {
        this.inPartition = inPartition;
    }

    public ServiceRoute(GeneralTask service, RequestBinder binder, boolean inPartition) {
        this.service = service;
        this.requestBinder = binder;
        this.inPartition = inPartition;
    }

    public ServiceRoute(GeneralTask service, RequestBinder binder) {
        this(service, binder,false);
    }

    public ServiceRoute setGigaspaces(GigaSpace gigaSpace) {
        this.gigaSpace = gigaSpace;
        return this;
    }

    public ServiceRoute setRefTableCache(RefTableCache refTableCache, RefTableCache refDocTableCache) {
        this.refTableCache= refTableCache;
        this.refDocTableCache = refDocTableCache;

        return this;
    }

    public Object handle(Request req, Response res) throws Exception {

        LogBuilder.init(req);

        // todo add tracing for e.g. zipkin
        processedRequests.incrementAndGet();
        long startMillis = System.currentTimeMillis();
        try {
            service.request = requestBinder.bind(req);
            if (inPartition) {
                // pushing the task to the grid
                ArrayList<ServiceResponse> result = gigaSpace.execute(service).get(); //using as lambda function
                this.lastResultsCount = result.size();
                return new TResults<>(result);
            } else {
                // working in current thread
                service.gigaSpace = gigaSpace;
                service.refTableCache = refTableCache;
                service.refDocTableCache = refDocTableCache;
                ArrayList<ServiceResponse> result = service.execute();
                this.lastResultsCount = result.size();
                return new TResults<>(result);
            }
        } catch (Throwable t) {
            this.lastResultsCount = 0;
            logger.error("Service Failed - url: " + req.url() + " error : " + t.getMessage(), t);
            throw new ServiceException("Service Failed", ErrorMessage.FAIL_TO_RUN_USECASE, t);
        } finally {
            this.lastLatency = System.currentTimeMillis() - startMillis;
        }
    }

    @Override
    public void setProcessingUnitContainerContext(ProcessingUnitContainerContext processingUnitContainerContext) {
        this.beanMetricManager = processingUnitContainerContext.createBeanMetricManager("ServiceRoute");
        this.beanMetricManager.register("service-requests", new Gauge<Integer>() {
            @Override
            public Integer getValue() throws Exception {
                return processedRequests.get();
            }
        });
        this.beanMetricManager.register("service-latency", new Gauge<Long>() {
            @Override
            public Long getValue() throws Exception {
                return lastLatency;
            }
        });
        this.beanMetricManager.register("service-results-count", new Gauge<Integer>() {
            @Override
            public Integer getValue() throws Exception {
                return lastResultsCount;
            }
        });
    }
}
