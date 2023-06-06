package com.gs.usecase;

import org.slf4j.Logger;
import java.sql.Date;
import tau.ods.gs.model.logging.LoggerFactory;
import tau.ods.gs.model.logging.LogBuilder;
import tau.ods.gs.model.logging.LogMessage;
import com.gs.infra.health.HealthChecker;
import org.openspaces.core.GigaSpace;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


@Component
public class {{service.name}}AdvancedHealthChecker implements HealthChecker {

    private static final Logger logger = LoggerFactory.getLogger({{service.name}}AdvancedHealthChecker.class);
    static private final String type = "{{service.type}}";

    @Resource
    GigaSpace gigaSpace;

    @Override
    public int isHealthy() {

        logger.info(LogBuilder.get()
        .setStatusCode(200)
        .setLevel(LogMessage.Level.INFO)
        .setTimestamp(new Date(System.currentTimeMillis()))
        .setMessage("going to run health for KR_CHEDER and verify if type " + type + " exist in bllspace")
        .createLogMessage());

        if (gigaSpace.getTypeManager().getTypeDescriptor(type) == null) {
            logger.error("{{service.name}} service is un-health because type " + type + " doesn't exist in bllspace");

            return 500;
        }else{
            logger.info("Type "+type+" exist in bllspace");
            return 200;
        }
    }
}
