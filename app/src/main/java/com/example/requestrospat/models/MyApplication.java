package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyApplication {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("filing_date")
    @Expose
    private String filingDate;

    public String getNumber() {
        return number;
    }

    public String getFilingDate() {
        return filingDate;
    }
}
