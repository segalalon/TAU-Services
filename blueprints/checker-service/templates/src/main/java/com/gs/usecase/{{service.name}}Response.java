package com.gs.usecase;

import com.gs.infra.service.ServiceResponse;
import com.gigaspaces.document.SpaceDocument;

/**
 * a Response
 */
public class {{service.name}}Response implements ServiceResponse {

    public {{service.name}}Response(Integer count){
        this.count = count;
    }

    public {{service.name}}Response(SpaceDocument spaceDocument){
        this.spaceDocument = spaceDocument;
    }

    Integer count;
    SpaceDocument spaceDocument;

@Override
public String toString() {
        return "Ms_int_chk_1001_s1_Output{" +
        "count='" + count + '\'' +
        "spaceDocument='" + spaceDocument + '\'' +
        '}';
        }
}
