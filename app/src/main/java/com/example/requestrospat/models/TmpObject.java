package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TmpObject {
    @SerializedName("values")
    @Expose
    private ArrayList<String> values = new ArrayList();

    public TmpObject(ArrayList<String> values) {
        this.values = values;
    }
}
