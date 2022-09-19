package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("inventor")
    @Expose
    private String inventor;
    @SerializedName("patentee")
    @Expose
    private String patentee;
    @SerializedName("classification")
    @Expose
    private Classification_1 classification;
    @SerializedName("applicant")
    @Expose
    private String applicant;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLang() {
        return lang;
    }

    public String getInventor() {
        return inventor;
    }

    public String getPatentee() {
        return patentee;
    }

    public Classification_1 getClassification() {
        return classification;
    }

    public String getApplicant() {
        return applicant;
    }

    public class Classification_1 {
        @SerializedName("ipc")
        @Expose
        private String ipc;

        public String getIpc() {
            return ipc;
        }
    }
}
