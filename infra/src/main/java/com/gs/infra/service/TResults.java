package com.gs.infra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TResults<T> implements IResult {
    ArrayList<T> res;

    public TResults(ArrayList<T> res) {
        this.res = res;
    }

    @Override
    public List<Object> getResult() {
        return Arrays.asList(res.toArray());
    }
}
