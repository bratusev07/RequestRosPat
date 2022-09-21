package com.example.requestrospat.models;

public class SameModel {
    private String type_search;
    private String pat_id;
    private int count;

    public SameModel(String pat_id, int count) {
        this.type_search = "id_search";
        this.pat_id = pat_id;
        this.count = count;
    }

    public void setType_search(String type_search) {
        this.type_search = type_search;
    }

    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
