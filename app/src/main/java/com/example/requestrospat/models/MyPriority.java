package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPriority {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("filing_date")
    @Expose
    private String filingDate;
    @SerializedName("publishing_office")
    @Expose
    private String publishingOffice;

    public String getNumber() {
        return number;
    }

    public String getFilingDate() {
        return filingDate;
    }

    public String getPublishingOffice() {
        return publishingOffice;
    }
}
