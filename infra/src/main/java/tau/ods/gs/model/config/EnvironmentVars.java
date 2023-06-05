package tau.ods.gs.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVars {

    @Value("${env.ecs.version}")
    private String escVersion;

    @Value("${env.http.version}")
    private String httpVersion;

    @Value("${env.appInfo.name}")
    private String appInfoName;

    @Value("${env.appInfo.cmdbId}")
    private String appInfoCmdbId;

    @Value("${logging.folder}")
    private String loggingFolder;

    @Value("${logging.serviceFileHandler.limit}")
    private Long loggingServiceFileHandlerLimit;

    @Value("${logging.serviceFileHandler.count}")
    private Integer loggingServiceFileHandlerCount;

    @Value("${logging.serviceFileHandler.append}")
    private Boolean loggingServiceFileHandlerAppend;

    @Value("${env.swagger.linkToSwaggerHub}")
    private String linkToSwaggerHub;

    @Value("${env.service.timeout}")
    private Long timeout;

    public String getEscVersion() {
        return escVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getAppInfoName() {
        return appInfoName;
    }

    public String getAppInfoCmdbId() {
        return appInfoCmdbId;
    }

    public String getLoggingFolder() {
        return loggingFolder;
    }

    public Long getLoggingServiceFileHandlerLimit() {
        return loggingServiceFileHandlerLimit;
    }

    public Integer getLoggingServiceFileHandlerCount() {
        return loggingServiceFileHandlerCount;
    }

    public Boolean getLoggingServiceFileHandlerAppend() {
        return loggingServiceFileHandlerAppend;
    }

    public String getLinkToSwaggerHub() { return linkToSwaggerHub; }

    public Long getTimeout() { return timeout; }
}
