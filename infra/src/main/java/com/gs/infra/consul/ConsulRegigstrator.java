package com.gs.infra.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConsulRegigstrator {

    private String consulHost="";
    private int consulPort=8500;
    private String serviceId;
    private String requiredRole;
    private String serviceName;
    private int servicePort;
    private AgentClient agentClient;
    private String version;

    public ConsulRegigstrator(String consulHost, int consulPort, String serviceId, String requiredRole, int servicePort, String version){
        this.consulHost = consulHost;
        this.consulPort = consulPort;
        this.serviceId = serviceId;
        this.requiredRole = requiredRole;
        this.servicePort = servicePort;
        this.version = version;
        agentClient = createAgentClient();
        int lastDash = serviceId.lastIndexOf("-");
        serviceName = serviceId.substring(0, lastDash);
    }

    public void register() throws UnknownHostException {
        HostAndPort hostAndPort = HostAndPort.fromParts(consulHost, consulPort);
        Consul client = Consul.builder().withHostAndPort(hostAndPort).build();
        AgentClient agentClient = client.agentClient();
        String address = InetAddress.getLocalHost().getHostAddress();

        Registration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name(serviceName)
                .port(servicePort)
                .address(address)
                .putMeta("Authorization", requiredRole)

                .check(ImmutableRegCheck.builder().http("http://" + address + ":" + servicePort
                        + "/" + version + "/actuator/health").interval("30s").deregisterCriticalServiceAfter("5m").build()).build();

//                .check(Registration.RegCheck.http("http://" + address + ":" + servicePort
//                        + "/" + version + "/actuator/health", 30)).build();

        agentClient.register(service);
    }

    public void deregister(){
        agentClient.deregister(serviceId);
    }

    private AgentClient createAgentClient() {
        HostAndPort hostAndPort = HostAndPort.fromParts(consulHost, consulPort);
        Consul client = Consul.builder().withHostAndPort(hostAndPort).build();
        AgentClient agentClient = client.agentClient();
        return agentClient;
    }
}
