package com.foodway.api.record.DTOs;

import java.util.List;

public class MapsLongLag {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MapsLongLag{" +
                "results=" + results +
                '}';
    }
}
