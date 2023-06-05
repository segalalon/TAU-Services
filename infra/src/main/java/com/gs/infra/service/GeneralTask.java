package com.gs.infra.service;

import com.gs.lib.grid.RefTableCache;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.executor.Task;
import org.openspaces.core.executor.TaskGigaSpace;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;


public abstract class GeneralTask<ServiceRequest, ServiceResponse> implements Task<ArrayList<ServiceResponse>>, Serializable {

    private static final long serialVersionUID = 1234567L;
    protected static Properties properties;

    static {
        try {
            Class.forName("com.j_spaces.jdbc.driver.GDriver");
            properties = new Properties();
            try (InputStream is = GeneralTask.class.getResourceAsStream("/application.properties")) {
                properties.load(is);
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TaskGigaSpace
    public transient GigaSpace gigaSpace;

    public transient RefTableCache refTableCache;

    public transient RefTableCache refDocTableCache;

    public ServiceRequest request;

    public abstract Integer routing();

    public abstract ArrayList<ServiceResponse> execute();

}
