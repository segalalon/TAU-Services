package com.gs.lib.grid;

import com.gigaspaces.document.SpaceDocument;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

import java.io.Serializable;

public class RefTableLookup<TableEntry> implements Serializable {

    GigaSpace gigaSpace;
    Class<TableEntry> table;
    String type;

    public RefTableLookup(GigaSpace gigaSpace, Class<TableEntry> table) {
        this.gigaSpace = gigaSpace;
        this.table = table;
    }

    public RefTableLookup(GigaSpace gigaSpace, String type) {
        this.gigaSpace = gigaSpace;
        this.type = type;
    }

    public TableEntry lookup(String keyField, int key) {
        SQLQuery<TableEntry> sqlQuery = new SQLQuery(table, keyField + " = " + key);

        return gigaSpace.read(sqlQuery);
    }

    public SpaceDocument[] lookup(String type, String query) {
        SQLQuery<SpaceDocument> sqlQuery = new SQLQuery<>(type, query);

        return gigaSpace.readMultiple(sqlQuery);
    }
}