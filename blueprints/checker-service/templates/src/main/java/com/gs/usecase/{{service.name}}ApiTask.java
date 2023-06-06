package com.gs.usecase;

import org.slf4j.Logger;
import java.sql.Date;
import tau.ods.gs.model.logging.LoggerFactory;
import tau.ods.gs.model.logging.LogBuilder;
import tau.ods.gs.model.logging.LogMessage;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import org.openspaces.core.GigaSpaceTypeManager;
import java.util.ArrayList;
import com.gs.infra.service.GeneralTask;
import org.openspaces.core.executor.Task;
import com.gigaspaces.document.SpaceDocument;
import com.j_spaces.core.client.SQLQuery;

/**
 * Count a type
 */
public class {{service.name}}ApiTask extends GeneralTask<{{service.name}}Request, {{service.name}}Response> implements Task<ArrayList<{{service.name}}Response>> {

    private static final Logger logger = LoggerFactory.getLogger({{service.name}}ApiTask.class);


    static private final String type = "{{service.type}}";

    public ArrayList<{{service.name}}Response>execute() {

        ArrayList<{{service.name}}Response> responseList = new ArrayList<>();

        GigaSpaceTypeManager gigaSpaceTypeManager = gigaSpace.getTypeManager();
        SpaceTypeDescriptor spaceTypeDescriptor = gigaSpaceTypeManager.getTypeDescriptor(type);
        Integer count;
        SQLQuery<SpaceDocument> sqlQuery = null;

        if (spaceTypeDescriptor != null) {
            sqlQuery = new SQLQuery<SpaceDocument>(type,"");
            count = gigaSpace.count(sqlQuery);
            logger.info("There are " + count + " records of type " + type + " in bllspace");
            responseList.add(new {{service.name}}Response(count));

            SpaceDocument[] results = gigaSpace.readMultiple(sqlQuery,5);
            for (SpaceDocument entry : results) {
            responseList.add(new {{service.name}}Response(entry));
            }

        }else{
            logger.error("Ms_int_chk_1001_s{number} service is un-health because type " + type + " doesn't exist in bllspace");

            return null;
        }

        logger.info(LogBuilder.get()
        .setStatusCode(200)
        .setLevel(LogMessage.Level.INFO)
        .setTimestamp(new Date(System.currentTimeMillis()))
        .setMessage("Query: " + sqlQuery.getQuery())
        .createLogMessage());

        return responseList;
    }

    @Override
    public Integer routing() {
        return null;
    }
}
