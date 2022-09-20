package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Classification {
    @SerializedName("ipc")
    @Expose
    private List<Ipc> ipc = null;
    @SerializedName("cpc")
    @Expose
    private List<Cpc> cpc = null;

    public List<Ipc> getIpc() {
        return ipc;
    }

    public List<Cpc> getCpc() {
        return cpc;
    }


    public class Cpc {
        @SerializedName("main_group")
        @Expose
        private String mainGroup;
        @SerializedName("classification_value")
        @Expose
        private String classificationValue;
        @SerializedName("subgroup")
        @Expose
        private String subgroup;
        @SerializedName("subclass")
        @Expose
        private String subclass;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("class")
        @Expose
        private String _class;

        public String getMainGroup() {
            return mainGroup;
        }

        public String getClassificationValue() {
            return classificationValue;
        }

        public String getSubgroup() {
            return subgroup;
        }

        public String getSubclass() {
            return subclass;
        }

        public String getSection() {
            return section;
        }

        public String getFullname() {
            return fullname;
        }

        public String get_class() {
            return _class;
        }
    }

    public class Ipc {
        @SerializedName("main_group")
        @Expose
        private String mainGroup;
        @SerializedName("classification_value")
        @Expose
        private String classificationValue;
        @SerializedName("subgroup")
        @Expose
        private String subgroup;
        @SerializedName("subclass")
        @Expose
        private String subclass;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("class")
        @Expose
        private String _class;

        public String getMainGroup() {
            return mainGroup;
        }

        public String getClassificationValue() {
            return classificationValue;
        }

        public String getSubgroup() {
            return subgroup;
        }

        public String getSubclass() {
            return subclass;
        }

        public String getSection() {
            return section;
        }

        public String getFullname() {
            return fullname;
        }

        public String get_class() {
            return _class;
        }
    }
}
