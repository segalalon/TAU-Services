package com.gs.infra.health;

import org.openspaces.core.GigaSpace;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.rmi.RemoteException;

@Component
public class SpaceHealthChecker implements HealthChecker {

    @Resource
    GigaSpace gigaSpace;

    @Override
    public int isHealthy() throws RemoteException {

        return gigaSpace.getSpace().getDirectProxy().getRemoteJSpace().getSpaceHealthStatus().isHealthy() ? 200 : 500;
    }
}
