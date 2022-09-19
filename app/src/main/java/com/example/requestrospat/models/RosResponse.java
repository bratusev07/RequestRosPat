package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RosResponse {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("available")
    @Expose
    private Integer available;
    @SerializedName("hits")
    @Expose
    private List<Hit> hits = null;
    @SerializedName("timings")
    @Expose
    private Timings timings;

    public Integer getTotal() {
        return total;
    }

    public Integer getAvailable() {
        return available;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public Timings getTimings() {
        return timings;
    }
}
