package com.gs.infra.metadata;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public interface ServiceCatalog {

    JsonObject getMetaData() throws RemoteException;

}
