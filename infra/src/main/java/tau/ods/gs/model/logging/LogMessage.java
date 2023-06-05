package tau.ods.gs.model.logging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.sql.Wrapper;
import java.util.LinkedHashMap;


@JsonPropertyOrder({
        "ecs",
        "appInfo",
        "level",
        "message",
        "timestamp",
        "transaction",
        "error",
        "client",
        "http"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogMessage {

    public enum Level {
        TRACE, DEBUG, INFO, ERROR, CRITICAL, AUDIT, WARN, FATAL;
    }

    private ECS ecs = new ECS();

    @JsonProperty("AppInfo")
    private AppInfo appInfo = new AppInfo();

    @JsonProperty("Level")
    private String level;
    private String message;

    @JsonProperty("@timestamp")
    private String timestamp;
    private Transaction transaction = new Transaction();

    //todo change it to null
    private Error error = null;

    private Client client = new Client();
    private Http http = new Http();

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ECS getEcs() {
        return ecs;
    }

    public void setEcs(ECS ecs) {
        this.ecs = ecs;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @JsonPropertyOrder({
            "name",
            "cmdbId",
            "service"
    })
    public static class AppInfo {
        private String name;
        private String cmdbId;

        @JsonProperty("Service")
        private Service service = new Service();

        public static class Service {

            private String name;

            private Service() {}

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        private AppInfo() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCmdbId() {
            return cmdbId;
        }

        public void setCmdbId(String cmdbId) {
            this.cmdbId = cmdbId;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }
    }

    public static class ECS {
        private ECS() {}

        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    @JsonPropertyOrder({
            "ip",
//            "address"
    })
    public static class Client {

        private Client() {}

        private String ip;
//        private String address;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

//        public String getAddress() {
//            return address;
//        }

//        public void setAddress(String address) {
//            this.address = address;
//        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "code",
            "id",
            "message",
            "type",
            "stackTrace"
    })
       public static class Error {
        private String code;
        private Integer id;
        private String message;
        private String type;

        @JsonProperty("stack_trace")
        private String stackTrace;

        private Error() {}

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStackTrace() {
            return stackTrace;
        }

        public void setStackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
        }
    }


    public void instantiateError(){
        this.error = new Error();
    }

    public void nullError(){
        this.error = null;
    }

    public void instantiateHttpRequest(){
        this.http.request = new Http.Request();
    }

    public void nullHttpRequest(){
        this.http.request = null;
    }

    public void instantiateHttpRespose(){
        this.http.response = new Http.Response();
    }

    public void nullHttpRespose(){
        this.http.response = null;
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "request",
            "response",
            "version"
    })
    public static class Http {
        private Request request = null;
        private Response response = null;
        private String version;

        private Http() {}

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Request {
            private String method;

            @JsonProperty("QueryString")
            private String queryString;

            @JsonProperty("RemoteIpAddress")
            private String remoteIpAddress;

            @JsonProperty("Uri")
            private String uri;

            @JsonProperty("Headers")
            private LinkedHashMap<String, String> headers = new LinkedHashMap<>();

            private Request() {}

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public String getQueryString() {
                return queryString;
            }

            public void setQueryString(String queryString) {
                this.queryString = queryString;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public String getRemoteIpAddress() {
                return remoteIpAddress;
            }

            public void setRemoteIpAddress(String remoteIpAddress) {
                this.remoteIpAddress = remoteIpAddress;
            }

            public LinkedHashMap<String, String> getHeaders() {
                return headers;
            }

            public void setHeaders(LinkedHashMap<String, String> headers) {
                this.headers = headers;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "latency",
                "statusCode",
                "headers",
                "body"
        })
        public static class Response {

            @JsonProperty("Latency")
            private String latency;

            @JsonProperty("status_code")
            private Integer statusCode;

            @JsonProperty("Headers")
            private LinkedHashMap<String, String> headers = new LinkedHashMap<>();
            private Body body = new Body();

            public static class Body {
                private String content;

                private Body() {}

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public Integer getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(Integer statusCode) {
                this.statusCode = statusCode;
            }

            public LinkedHashMap<String, String> getHeaders() {
                return headers;
            }

            public void setHeaders(LinkedHashMap<String, String> headers) {
                this.headers = headers;
            }

            public Body getBody() {
                return body;
            }

            public void setBody(Body body) {
                this.body = body;
            }

            public String getLatency() {
                return latency;
            }

            public void setLatency(String latency) {
                this.latency = latency;
            }
        }

        public Request getRequest() {
            return request;
        }

        public void setRequest(Request request) {
            this.request = request;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public static class Transaction {
        private String id;

        private Transaction() {}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}