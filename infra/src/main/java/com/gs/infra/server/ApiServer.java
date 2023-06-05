package com.gs.infra.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.gs.infra.consul.ConsulRegigstrator;
import com.gs.infra.health.HealthReporter;
import com.gs.infra.metadata.ServiceCatalog;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SocketUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static spark.Spark.*;
import static spark.Spark.get;

public abstract class ApiServer {
    private static final Logger logger = LoggerFactory.getLogger(ApiServer.class);

    @Resource
    protected GigaSpace gigaSpace;
    final protected Gson gson = new Gson();

    protected int port;
    protected int minPort;
    protected int maxPort;
    protected String serviceId;
    protected String requiredRole;
    protected String consulHost;
    protected int consulPort;
    protected HealthReporter healthReporter;
    protected ConsulRegigstrator consulRegigstrator;
    protected String version;
    protected RequestHandler requestHandler;

    public ApiServer(int minPort, int maxPort, String serviceId, String requiredRole, String consulHost,
                     int consulPort, HealthReporter healthReporter, String version, RequestHandler requestHandler, ServiceCatalog serviceCatalog) {
        this.minPort = minPort;
        this.maxPort = maxPort;
        this.serviceId = serviceId;
        this.requiredRole = requiredRole;
        this.consulHost = consulHost;
        this.consulPort = consulPort;
        this.healthReporter = healthReporter;
        this.version = version;
        this.requestHandler = requestHandler;
    }

    @PostConstruct
    public void initializeServer() throws UnknownHostException, JsonProcessingException {
        final Gson gson = new Gson();
        port = SocketUtils.findAvailableTcpPort(minPort, maxPort);
        port(port);
        init();
        String url = InetAddress.getLocalHost().getHostAddress() + ":" + port;
        System.out.println("listening on " + url);
        logger.info("API server was initialized, url:  {}", url);
        if (!consulHost.isEmpty()) {
            logger.info("API server registering to consul url: {}", InetAddress.getLocalHost().getHostAddress());
            consulRegigstrator = new ConsulRegigstrator(consulHost, consulPort, serviceId, requiredRole, port, version);
            consulRegigstrator.register();
        }

        // Add routes for api commands
        path("/" + version, () -> { //creates path /servicename/version
            get("/actuator/health", requestHandler.health, gson::toJson); //creates path /servicename/version/actuator/health
        });

        path("/" + version, () -> { //creates path /servicename/version
            get("/metadata", requestHandler.metadata, gson::toJson); //creates path /servicename/version/metadata
        });
        definePaths();
    }

    @PreDestroy
    public void close() {
        logger.info("Closing service: {}", serviceId);
        stop();
        consulRegigstrator.deregister();
    }

    public abstract void definePaths() throws JsonProcessingException;

}

