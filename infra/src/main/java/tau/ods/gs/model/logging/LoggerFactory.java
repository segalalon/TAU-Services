package tau.ods.gs.model.logging;

import tau.ods.gs.model.config.EnvironmentVars;
import tau.ods.gs.model.config.ModelConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.LogManager;

public class LoggerFactory {
    private static final Logger logger =
            org.slf4j.LoggerFactory.getLogger(LoggerFactory.class);

    private static ServiceFileHandler fileHandler;

    static {
        if(StringUtils.isEmpty(System.getProperty("isRunner"))) {
            try {
                ApplicationContext appContext = ModelConfig.getAppContext();
                EnvironmentVars envVars = appContext.getBean(EnvironmentVars.class);

                String logDir = envVars.getLoggingFolder();
                new File(logDir).mkdirs();

                fileHandler = new ServiceFileHandler(
                        (logDir + "/" + envVars.getAppInfoName() + ".log").replace("/", File.separator),
                        envVars.getLoggingServiceFileHandlerLimit(),
                        envVars.getLoggingServiceFileHandlerCount(),
                        envVars.getLoggingServiceFileHandlerAppend()
                );

                ServiceFormatter serviceFormatter = new ServiceFormatter();
                serviceFormatter.setFormat("%5$s%n");

                fileHandler.setFormatter(serviceFormatter);
            } catch (Exception ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger slfLogger = org.slf4j.LoggerFactory.getLogger(clazz);

        if(StringUtils.isEmpty(System.getProperty("isRunner"))) {
            java.util.logging.Logger logger =
                    LogManager.getLogManager().getLogger(clazz.getName());

            for (Handler handler : logger.getHandlers()) {
                logger.removeHandler(handler);
            }
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        }

        return slfLogger;
    }
}
