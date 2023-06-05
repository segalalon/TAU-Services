package com.gs.usecase;

import java.sql.Date;
import org.slf4j.Logger;
import bll.ods.gs.model.logging.LoggerFactory;
import bll.ods.gs.model.logging.LogBuilder;
import bll.ods.gs.model.logging.LogMessage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gs.infra.metadata.ServiceCatalog;
import org.springframework.stereotype.Component;

@Component
public class {{service.name}}ServiceCatalogImpl implements ServiceCatalog {

    private static final Logger logger = LoggerFactory.getLogger({{service.name}}ServiceCatalogImpl.class);
    static private final String type = "{{service.type}}";

    @Override
    public JsonObject
    getMetaData() {

        logger.info(LogBuilder.get()
        .setStatusCode(200)
        .setLevel(LogMessage.Level.INFO)
        .setTimestamp(new Date(System.currentTimeMillis()))
        .setMessage("Query: metadata")
        .createLogMessage());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("endpoint", "{{service.name}}");
        jsonObject.addProperty("project", "{{service.project}}");
        jsonObject.addProperty("description", "{{service.description}}");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(type);

        jsonObject.add("metadata", jsonArray);

        return jsonObject;
    }
}
