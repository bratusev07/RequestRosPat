package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyBaseModel {
    private String q;
    private int limit;
    private int offset;
    private String pre_tag, post_tag;
    private String sort;
    private String group_by;
    private int include_facets;
    private MyFilter filter;

    public MyBaseModel(String q) {
        this.q = q;
        limit = 10;
        offset = 0;
        pre_tag = "<em>";
        post_tag = "</em>";
        sort = "relevance";
        include_facets = 0;
        filter = new MyFilter();
    }

    public void setQ(String q) {
        this.q = q;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setPre_tag(String pre_tag) {
        this.pre_tag = pre_tag;
    }

    public void setPost_tag(String post_tag) {
        this.post_tag = post_tag;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setGroup_by(String group_by) {
        this.group_by = group_by;
    }

    public void setInclude_facets(int include_facets) {
        this.include_facets = include_facets;
    }

    public void setFilter(MyFilter filter) {
        this.filter = filter;
    }

    public static class MyFilter {
        private TmpObject authors;
        private TmpObject patent_holders;
        private TmpObject country;
        private TmpObject kind;


        public void setAuthors(TmpObject authors) {
            this.authors = authors;
        }

        public void setPatent_holders(TmpObject patent_holders) {
            this.patent_holders = patent_holders;
        }

        public void setCountry(TmpObject country) {
            this.country = country;
        }

        public void setKind(TmpObject kind) {
            this.kind = kind;
        }
    }
}
