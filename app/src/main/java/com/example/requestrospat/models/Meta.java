package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("source")
    @Expose
    private MySource source;

    public MySource getSource() {
        return source;
    }

    public class MySource {
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("path")
        @Expose
        private String path;

        public String getFrom() {
            return from;
        }

        public String getPath() {
            return path;
        }
    }
}
