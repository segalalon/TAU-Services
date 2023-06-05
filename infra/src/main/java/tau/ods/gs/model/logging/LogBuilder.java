package tau.ods.gs.model.logging;

import tau.ods.gs.model.config.EnvironmentVars;
import tau.ods.gs.model.config.ModelConfig;
import tau.ods.gs.util.Const;
import tau.ods.gs.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hsqldb.persist.Log;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LogBuilder {

    private LogMessage logMessage = new LogMessage();

    private final static ThreadLocal<LogMessage> commonInfo = new ThreadLocal<>();

    private LogBuilder() {}

    public static Request localRequest;

    private void fillRequest(){
        HttpServletRequest servletRequest = localRequest.raw();

        logMessage.getHttp().getRequest().setMethod(servletRequest.getMethod());
        logMessage.getHttp().getRequest().setQueryString(servletRequest.getQueryString());
        logMessage.getHttp().getRequest().setUri(servletRequest.getRequestURI());
        logMessage.getHttp().getRequest().setRemoteIpAddress(servletRequest.getRemoteHost());

        Enumeration<String> headersNames = servletRequest.getHeaderNames();
        while (headersNames.hasMoreElements()) {
            String headerName = headersNames.nextElement();
            logMessage.getHttp().getRequest().getHeaders().put(headerName, servletRequest.getHeader(headerName));
        }
    }

    public static void init(Request request) {
        LogMessage logMessage = new LogMessage();
        commonInfo.set(logMessage);

       localRequest = request;


        HttpServletRequest servletRequest = request.raw();
        logMessage.getTransaction().setId(servletRequest.getHeader(Const.TRANSACTION_ID_HEADER));

//        logMessage.getClient().setAddress(servletRequest.getRemoteHost());
        logMessage.getClient().setIp(getClientIpAddress(servletRequest));
//
//        logMessage.getHttp().getRequest().setMethod(servletRequest.getMethod());
//        logMessage.getHttp().getRequest().setQueryString(servletRequest.getQueryString());
//        logMessage.getHttp().getRequest().setUri(servletRequest.getRequestURI());
//        logMessage.getHttp().getRequest().setRemoteIpAddress(servletRequest.getRemoteHost());
//
//        Enumeration<String> headersNames = servletRequest.getHeaderNames();
//        while (headersNames.hasMoreElements()) {
//            String headerName = headersNames.nextElement();
//            logMessage.getHttp().getRequest().getHeaders().put(headerName, servletRequest.getHeader(headerName));
//        }
    }

    public static LogBuilder get() {
        LogBuilder logBuilder = new LogBuilder();

        LogMessage commonInfo = LogBuilder.commonInfo.get();

        EnvironmentVars envVars = ModelConfig.getAppContext().getBean(EnvironmentVars.class);

        logBuilder.logMessage.getEcs().setVersion(envVars.getEscVersion());
        logBuilder.logMessage.getAppInfo().setName(envVars.getAppInfoName());
        logBuilder.logMessage.getAppInfo().getService().setName(envVars.getAppInfoName());
        logBuilder.logMessage.getAppInfo().setCmdbId(envVars.getAppInfoCmdbId());
        logBuilder.logMessage.getHttp().setVersion(envVars.getHttpVersion());

        if(commonInfo != null) {
            logBuilder.logMessage.setTransaction(commonInfo.getTransaction());
            logBuilder.logMessage.setClient(commonInfo.getClient());
            logBuilder.logMessage.getHttp().setRequest(commonInfo.getHttp().getRequest());
        }
        return logBuilder;
    }

    public LogBuilder instantiateHttpRequest() {
        this.logMessage.instantiateHttpRequest();
        fillRequest();
        return this;
    }

    public LogBuilder instantiateHttpResponse() {
        this.logMessage.instantiateHttpRespose();
        return this;
    }

    public LogBuilder nullHttpRequest() {
        this.logMessage.nullHttpRequest();
        return this;
    }

    public LogBuilder nullHttpResponse() {
        this.logMessage.nullHttpRespose();
        return this;
    }

    public LogBuilder instantiateError() {
        this.logMessage.instantiateError();
        return this;
    }

    public LogBuilder nullError() {
        this.logMessage.nullError();
        return this;
    }

    public LogBuilder setErrorId(Integer errorId) {
        this.logMessage.getError().setId(errorId);
        return this;
    }

    public LogBuilder setErrorCode(Integer errorCode) {
        this.logMessage.getError().setCode(errorCode != null ? ""+errorCode : null);
        this.logMessage.getHttp().getResponse().setStatusCode(errorCode);
        return this;
    }

    public LogBuilder setErrorMessage(String errorMessage) {
        this.logMessage.setMessage(errorMessage);
        this.logMessage.getError().setMessage(errorMessage);
        this.logMessage.getHttp().getResponse().getBody().setContent(errorMessage);
        return this;
    }

    public LogBuilder setErrorType(String errorType) {
        this.logMessage.getError().setMessage(errorType);
        return this;
    }

    public LogBuilder setErrorStackTrace(Throwable throwable) {
        this.logMessage.getError().setStackTrace(ExceptionUtils.getStackTrace(throwable));
        return this;
    }

    public LogBuilder setLevel(LogMessage.Level level) {
        this.logMessage.setLevel(level.name().toLowerCase());
        return this;
    }

    public LogBuilder setTimestamp(Date date) {
        this.logMessage.setTimestamp(DateUtil.format(date, DateUtil.DateFormat.YYYY_MM_DD_T_HH_MM_SS_SSSZ));
        return this;
    }

    public LogBuilder setStatusCode(Integer statusCode) {
        this.logMessage.getHttp().getResponse().setStatusCode(statusCode);
        return this;
    }


    public LogBuilder setMessage(String message) {
        this.logMessage.setMessage(message);
        this.logMessage.getHttp().getResponse().getBody().setContent(message);
        return this;
    }

    //todo
    public LogBuilder setRequestMessage(String message) {
        this.logMessage.setMessage(message);
        return this;
    }


    public LogBuilder setLatency(long latency) {
        this.logMessage.getHttp().getResponse().setLatency(""+latency);
        return this;
    }

    public LogBuilder setResponseHeaders(Request request) {
        this.logMessage.getHttp()
                .getResponse()
                .setHeaders(request.headers()
                        .stream()
                        .collect(
                                Collectors.toMap(Function.identity(),
                                        header->request.headers(header),
                                        (val1, val2) -> val2,
                                        LinkedHashMap::new)));
        return this;
    }


    public LogBuilder setResponseHeaders(Response response) {
        this.logMessage.getHttp()
                .getResponse()
                .setHeaders(response.raw().getHeaderNames()
                        .stream()
                        .collect(
                                Collectors.toMap(Function.identity(),
                                        header->response.raw().getHeader(header),
                                        (val1, val2) -> val2,
                                        LinkedHashMap::new)));
        return this;
    }


    public LogBuilder addResponseHeader(String key, String value) {
        this.logMessage.getHttp()
                .getResponse()
                .getHeaders()
                .put(key, value);
        return this;
    }

    private String getRequestHeaders() {
        final StringBuffer buf = new StringBuffer();
        logMessage.getHttp().getRequest().getHeaders().entrySet().stream()
                .forEach(e -> {
                    buf.append("'" + e.getKey() + "': '" + e.getValue() + "',");
                });
        return buf.substring(0, buf.length()-1);
    }


    public String createLogMessage() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(logMessage);
        } catch (JsonProcessingException ex) {
            EnvironmentVars envVars = ModelConfig.getAppContext().getBean(EnvironmentVars.class);
            return
                    "           { " +
                            "                'ecs':{" +
                            "                   'version':'" + envVars.getEscVersion() + "'" +
                            "                 }," +
                            "                'AppInfo':{" +
                            "                    'name':'" + envVars.getAppInfoName() + "', " +
                            "                    'cmdbId':'" + envVars.getAppInfoCmdbId() + "'" +
                            "                    'Service':{" +
                            "                        'name':'" + envVars.getAppInfoName() + "'" +
                            "                     }" +
                            "                }," +
                            "                'Level':'error'," +
                            "                'message':'Error JSON parse Exception: " + ex.getMessage() + "'," +
                            "                '@timestamp':'" + DateUtil.format(new Date(), DateUtil.DateFormat.YYYY_MM_DD_T_HH_MM_SS_SSSZ) + "'," +
                            "                'transaction':{'id':'" + logMessage.getTransaction().getId() + "'}," +
                            "                'error':{" +
                            "                        'code':null," +
                            "                        'id':null," +
                            "                        'message':'Error JSON parse Exception: " + ex.getMessage() + "'," +
                            "                        'type':null," +
                            "                        'stack_trace':'" + ExceptionUtils.getStackTrace(ex) + "'" +
                            "                 }," +
                            "                'client': {" +
                            "                        'ip':'" + logMessage.getClient().getIp() + "'," +
 //                           "                        'address':'" + logMessage.getClient().getAddress() + "'" +
                            "                 }," +
                            "                'http': {" +
                            "                    'request': {" +
                            "                       'method':'" + logMessage.getHttp().getRequest().getMethod() + "'," +
                            "                       'QueryString':'" + logMessage.getHttp().getRequest().getQueryString()  + "'," +
                            "                       'RemoteIpAddress':'" + logMessage.getHttp().getRequest().getRemoteIpAddress()  + "'," +
                            "                       'Uri':'" + logMessage.getHttp().getRequest().getUri()  + "'," +
                            "                       'Headers': { " + getRequestHeaders() + " }" +
                            "                    }," +
                            "                    'response':{" +
                            "                       'Latency':0," +
                            "                       'status_code':null," +
                            "                       'Headers':{}," +
                            "                       'body':{ 'content':'Error JSON parse Exception: " + ex.getMessage() + "' }" +
                            "                    }," +
                            "                    'version':'" + logMessage.getHttp().getVersion() + "'" +
                            "                } " +
                            "           } ";
        }
    }


    private static final String[] VALID_IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : VALID_IP_HEADER_CANDIDATES) {
            String ipAddress = request.getHeader(header);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                return ipAddress;
            }
        }
        return request.getRemoteAddr();
    }


//    public static String getClientIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
}
