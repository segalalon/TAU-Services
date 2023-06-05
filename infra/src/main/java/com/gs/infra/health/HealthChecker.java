package com.gs.infra.health;

import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public interface HealthChecker {

    int isHealthy() throws RemoteException;

}
