package com.gs.infra.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaseHealthChecker implements HealthChecker{
    private static final Logger logger = LoggerFactory.getLogger(BaseHealthChecker.class);

    @Override
    public int isHealthy() {
        logger.debug("BaseHealthChecker - isHealthy");
        return 200;
    }
}
