package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Hit implements Serializable {
    @SerializedName("common")
    @Expose
    private Common common;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("biblio")
    @Expose
    private Biblio biblio;
    @SerializedName("drawings")
    @Expose
    private List<Drawing> drawings = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("dataset")
    @Expose
    private String dataset;
    @SerializedName("similarity")
    @Expose
    private Double similarity;
    @SerializedName("similarity_norm")
    @Expose
    private Double similarityNorm;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    public Common getCommon() {
        return common;
    }

    public Meta getMeta() {
        return meta;
    }

    public Biblio getBiblio() {
        return biblio;
    }

    public List<Drawing> getDrawings() {
        return drawings;
    }

    public String getId() {
        return id;
    }

    public String getIndex() {
        return index;
    }

    public String getDataset() {
        return dataset;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public Double getSimilarityNorm() {
        return similarityNorm;
    }

    public Snippet getSnippet() {
        return snippet;
    }
}
