package com.gs.usecase;

import tau.ods.gs.model.logging.LogBuilder;
import tau.ods.gs.model.logging.LogMessage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gs.infra.metadata.ServiceCatalog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class KR_CHEDERServiceCatalogImpl implements ServiceCatalog {

    // private static final Logger logger_gsc = org.slf4j.LoggerFactory.getLogger(DB_Central_ForeignCurrencyTable_DKTB614ServiceCatalogImpl.class);
    private static final Logger logger_service = tau.ods.gs.model.logging.LoggerFactory.getLogger(KR_CHEDERServiceCatalogImpl.class);
    static private final String type = "STUD.KR_CHEDER";

    @Override
    public JsonObject
    getMetaData() {

        logger_service.info(LogBuilder.get()
                .setStatusCode(200)
                .setLevel(LogMessage.Level.INFO)
                .setTimestamp(new Date(System.currentTimeMillis()))
                .setMessage("Query: metadata")
                .createLogMessage());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("endpoint", "KR_CHEDER");
        jsonObject.addProperty("project", "STUD");
        jsonObject.addProperty("description", "This table defines TAU rooms");
        jsonObject.addProperty("reqested feilds", "K_BINYAN format: ^[0-9]{2}$  K_MIS_CHEDER format: ^[0-9]{3}$  K_ZIHUY_NOSAF format: ^[A-Z]{3}$");


        JsonArray jsonArray = new JsonArray();
        jsonArray.add(type);

        jsonObject.add("metadata", jsonArray);

        return jsonObject;
    }
}
