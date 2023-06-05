package com.gs.lib.grid;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

import java.io.Serializable;

public class DocRefTableLookup<SpaceDocument> implements Serializable {

    GigaSpace gigaSpace;
    String typeName;

    public DocRefTableLookup(GigaSpace gigaSpace, String typeName) {
        this.gigaSpace = gigaSpace;
        this.typeName = typeName;
    }

    public SpaceDocument lookup(String keyField, int key) {
        SQLQuery<SpaceDocument> sqlQuery = new SQLQuery(this.typeName, keyField + " = " + key);
        return gigaSpace.read(sqlQuery);
    }



}
