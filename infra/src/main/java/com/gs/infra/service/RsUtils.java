package com.gs.infra.service;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RsUtils {


    public static ResultSet getResultSet(String query, Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);
        return resultSet;
    }

    @NotNull
    public static Map<String, Object> getObjectMap(ResultSet resultSet, ResultSetMetaData md, int columns) throws SQLException {
        Map<String, Object> row = new HashMap<>();
        for (int i = 1; i <= columns; ++i) {
            row.put(md.getColumnName(i), resultSet.getObject(i));
        }
        return row;
    }

}
