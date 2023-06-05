package com.gs.infra.service;

import com.gs.infra.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class QResults implements IResult{
    Object[] res;

    public QResults(Object[] res){
        this.res =res;
    }



    public QResults(ErrorMessage errorMessage){
        res = new ErrorMessage[1];
        res[0] = errorMessage;
    }


    @Override
    public ArrayList<Object> getResult() {
        int capacity = res.length;
        ArrayList<Object> results = new ArrayList<>(capacity);
        results.addAll(Arrays.asList(res));
        return  results;
    }

    @Override
    public String toString() {
        return Arrays.toString(res);
    }
}
