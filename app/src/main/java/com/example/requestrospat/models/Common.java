package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Common {
    @SerializedName("publishing_office")
    @Expose
    private String publishingOffice;
    @SerializedName("document_number")
    @Expose
    private String documentNumber;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("publication_date")
    @Expose
    private String publicationDate;
    @SerializedName("application")
    @Expose
    private MyApplication application;
    @SerializedName("classification")
    @Expose
    private Classification classification;
    @SerializedName("priority")
    @Expose
    private List<MyPriority> priority = null;

    public String getPublishingOffice() {
        return publishingOffice;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getKind() {
        return kind;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public MyApplication getApplication() {
        return application;
    }

    public Classification getClassification() {
        return classification;
    }

    public List<MyPriority> getPriority() {
        return priority;
    }
}
