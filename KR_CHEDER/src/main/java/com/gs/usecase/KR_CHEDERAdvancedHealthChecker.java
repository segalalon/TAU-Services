package com.gs.usecase;

import com.gs.infra.health.HealthChecker;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


@Component
public class KR_CHEDERAdvancedHealthChecker implements HealthChecker {

 //   private static final Logger logger_gsc = org.slf4j.LoggerFactory.getLogger(DB_Central_ForeignCurrencyTable_DKTB614AdvancedHealthChecker.class);
    private static final Logger logger_service = tau.ods.gs.model.logging.LoggerFactory.getLogger(KR_CHEDERAdvancedHealthChecker.class);
    static private final String type = "STUD.KR_CHEDER";

    @Resource
    GigaSpace gigaSpace;

    @Override
    public int isHealthy() {

        if (gigaSpace.getTypeManager().getTypeDescriptor(type) == null) {
            return 500;
        }else{
            return 200;
        }
    }
}
