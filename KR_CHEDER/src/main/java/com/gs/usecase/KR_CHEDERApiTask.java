package com.gs.usecase;

import tau.ods.gs.model.logging.LogBuilder;
import tau.ods.gs.model.logging.LogMessage;
import com.gs.infra.service.ErrorServiceResponse;
import org.slf4j.Logger;
import java.sql.Date;
import java.util.ArrayList;
import com.gs.infra.service.GeneralTask;
import org.openspaces.core.executor.Task;
import com.gigaspaces.document.SpaceDocument;
import com.j_spaces.core.client.SQLQuery;
import static java.lang.String.format;


public class KR_CHEDERApiTask extends GeneralTask<KR_CHEDERRequest, KR_CHEDERResponse> implements Task<ArrayList<KR_CHEDERResponse>> {

    private static final Logger logger_gsc = org.slf4j.LoggerFactory.getLogger(KR_CHEDERApiTask.class);
    private static final Logger logger_service = tau.ods.gs.model.logging.LoggerFactory.getLogger(KR_CHEDERApiTask.class);
    static private final String type = "STUD.KR_CHEDER";

    public ArrayList execute() {

        String USECASE_QUERY = null;
        String query = null;
        String binyan = request.getK_BINYAN();
        String misCheder = request.getK_MIS_CHEDER();
        String zihuyNosaf = request.getK_ZIHUY_NOSAF();
        String transactionId = request.getTransactionId();

        ArrayList responseList = new ArrayList<>();

        if (transactionId == null) {
            responseList.add(new ErrorServiceResponse("Invalid or missing request header", "400", "Error: Missing: 'x-transaction-id' header"));

            logger_service.error(LogBuilder.get()
                    .instantiateHttpRequest()
                    .nullHttpResponse()
                    .nullError()
                    .setRequestMessage("Query: binyan = " + binyan + " mispar cheder = " + misCheder + " zihuy nosaf " + zihuyNosaf)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .createLogMessage());

            logger_service.error(LogBuilder.get()
                    .instantiateHttpResponse()
                    .nullHttpRequest()
                    .instantiateError()
                    .setErrorCode(400)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .setErrorMessage("Invalid or missing request header: Error: Missing: 'x-transaction-id' header")
                    .createLogMessage());

            return responseList;

        }else if (binyan == null && misCheder != null && zihuyNosaf != null) {
            responseList.add(new ErrorServiceResponse("Invalid keys", "400", "keys: K_BINYAN - NaN, K_MIS_CHEDER - " + misCheder + "K_ZIHUY_NOSAF = " + zihuyNosaf));

            logger_service.error(LogBuilder.get()
                    .instantiateHttpRequest()
                    .nullHttpResponse()
                    .nullError()
                    .setRequestMessage("Query: binyan = " + binyan + " misCheder = " + misCheder + " zihuyNosaf = " + zihuyNosaf)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .createLogMessage());

            logger_service.error(LogBuilder.get()
                    .instantiateHttpResponse()
                    .nullHttpRequest()
                    .instantiateError()
                    .setErrorCode(400)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .setErrorMessage("Invalid keys: keys: K_BINYAN - NaN, K_MIS_CHEDER - " + misCheder + "K_ZIHUY_NOSAF = " + zihuyNosaf)
                    .createLogMessage());

            return responseList;

        }else if (binyan == null && misCheder == null && zihuyNosaf == null) {
            responseList.add(new ErrorServiceResponse("Invalid keys", "400", "keys: K_BINYAN - NaN, K_MIS_CHEDER - undefined, K_ZIHUY_NOSAF - undefined"));

            logger_service.error(LogBuilder.get()
                    .instantiateHttpRequest()
                    .nullHttpResponse()
                    .nullError()
                    .setRequestMessage("Query: binyan = " + binyan + " misCheder = " + misCheder + " zihuyNosaf = " + zihuyNosaf)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .createLogMessage());

            logger_service.error(LogBuilder.get()
                    .instantiateHttpResponse()
                    .nullHttpRequest()
                    .nullError()
                    .setErrorCode(400)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .setErrorMessage("Invalid keys: K_BINYAN - NaN, K_MIS_CHEDER - undefined, K_ZIHUY_NOSAF - undefined")
                    .createLogMessage());

            return responseList;

        }else if (binyan != null && binyan.length() != 2){
            responseList.add(new ErrorServiceResponse("Invalid value for: K_BINYAN, format: ^[0-9]{2}$", "400", "K_BINYAN: " + binyan));

            logger_service.error(LogBuilder.get()
                    .instantiateHttpRequest()
                    .nullHttpResponse()
                    .nullError()
                    .setRequestMessage("Query: binyan = " + binyan + " misCheder = " + misCheder + " zihuyNosaf = " + zihuyNosaf)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .createLogMessage());

            logger_service.error(LogBuilder.get()
                    .instantiateHttpResponse()
                    .nullHttpRequest()
                    .instantiateError()
                    .setErrorCode(400)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .setErrorMessage("Invalid value for: K_BINYAN, format: ^[0-9]{2}$: K_BINYAN: " + binyan)
                    .createLogMessage());

            return responseList;
        }else if (binyan != null && binyan.length() == 2 && misCheder == null){
            responseList.add(new ErrorServiceResponse("missing reqested feilds", "400", "K_MIS_CHEDER undefined dosent exist"));

            logger_service.error(LogBuilder.get()
                    .instantiateHttpRequest()
                    .nullHttpResponse()
                    .nullError()
                    .setRequestMessage("Query: binyan = " + binyan + " misCheder = " + misCheder + " zihuyNosaf = " + zihuyNosaf)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .createLogMessage());

            logger_service.error(LogBuilder.get()
                    .instantiateHttpResponse()
                    .nullHttpRequest()
                    .instantiateError()
                    .setErrorCode(400)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .setErrorMessage("missing reqested feilds: ^[0-9]{2}$: K_MIS_CHEDER undefined dosent exist")
                    .createLogMessage());

            return responseList;
        }else if (binyan != null && binyan.length() == 2 && !misCheder.equals("null") && misCheder.length() != 3) {
            responseList.add(new ErrorServiceResponse("Invalid value for: K_MIS_CHEDER, format: ^[0-9]{3}$", "400", "K_MIS_CHEDER: " + misCheder.length()));

            logger_service.error(LogBuilder.get()
                    .instantiateHttpRequest()
                    .nullHttpResponse()
                    .instantiateError()
                    .setRequestMessage("Query: binyan = " + binyan + " misCheder = " + misCheder + " zihuyNosaf = " + zihuyNosaf)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .createLogMessage());

            logger_service.error(LogBuilder.get()
                    .instantiateHttpResponse()
                    .nullHttpRequest()
                    .instantiateError()
                    .setErrorCode(400)
                    .setLevel(LogMessage.Level.ERROR)
                    .setTimestamp(new Date(System.currentTimeMillis()))
                    .setErrorMessage("Invalid value for: K_MIS_CHEDER, format: ^[0-9]{3}$: K_MIS_CHEDER: " + misCheder.length())
                    .createLogMessage());

            return responseList;
        }

        if (misCheder.equals("null")){

            USECASE_QUERY = "K_BINYAN" + " = %s ";

            query = format(USECASE_QUERY,
                    request.getK_BINYAN());

        }else {

            USECASE_QUERY = "K_BINYAN" + " = %s AND K_MIS_CHEDER = %s AND K_ZIHUY_NOSAF = %s";

            query = format(USECASE_QUERY,
                    request.getK_BINYAN(), request.getK_MIS_CHEDER(), request.getK_ZIHUY_NOSAF());
        }

        SQLQuery<SpaceDocument> sqlQuery = new SQLQuery<>(type, query);
        SpaceDocument[] results = gigaSpace.readMultiple(sqlQuery, 100);

        for (SpaceDocument entry : results) {
            responseList.add(new KR_CHEDERResponse(entry));

        }

        logger_service.info(LogBuilder.get()
                .instantiateHttpRequest()
                .nullHttpResponse()
                .nullError()
                .setRequestMessage("Query: " + sqlQuery.getQuery())
                .setLevel(LogMessage.Level.INFO)
                .setTimestamp(new Date(System.currentTimeMillis()))
                .createLogMessage());

        logger_service.info(LogBuilder.get()
                .instantiateHttpResponse()
                .nullHttpRequest()
                .nullError()
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