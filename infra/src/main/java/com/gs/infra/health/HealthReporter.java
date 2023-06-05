package com.gs.infra.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.List;

@Component
public class HealthReporter {
    private static final Logger logger = LoggerFactory.getLogger(HealthReporter.class);

    private static List<HealthChecker> healthCheckers;

    public HealthReporter() {}

    public int isHealthy() throws RemoteException {

        logger.debug("####REPORTER-start###### - {}", healthCheckers);
        int maxResponse = -1;
        for (HealthChecker hc : healthCheckers) {

            maxResponse = Math.max(maxResponse,hc.isHealthy());
        }
        return maxResponse;
    }

    public List<HealthChecker> getHealthCheckers() {
        return healthCheckers;
    }

    public static void setHealthCheckers(List<HealthChecker> healthCheckers) {
        HealthReporter.healthCheckers = healthCheckers;
    }

}
