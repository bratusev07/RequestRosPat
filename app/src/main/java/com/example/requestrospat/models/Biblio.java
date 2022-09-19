package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Biblio {
    @SerializedName("ru")
    @Expose
    private Ru ru;
    @SerializedName("en")
    @Expose
    private En en;

    public Ru getRu() {
        return ru;
    }

    public En getEn() {
        return en;
    }

    public class En {
        @SerializedName("inventor")
        @Expose
        private List<Inventor_1> inventor = null;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("patentee")
        @Expose
        private List<Patentee_1> patentee = null;

        public List<Inventor_1> getInventor() {
            return inventor;
        }

        public String getTitle() {
            return title;
        }

        public List<Patentee_1> getPatentee() {
            return patentee;
        }

        public class Inventor_1 {
            @SerializedName("name")
            @Expose
            private String name;

            public String getName() {
                return name;
            }
        }

        public class Patentee_1 {
            @SerializedName("name")
            @Expose
            private String name;

            public String getName() {
                return name;
            }
        }
    }

    public class Ru {
        @SerializedName("inventor")
        @Expose
        private List<Inventor> inventor = null;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("patentee")
        @Expose
        private List<Patentee> patentee = null;
        @SerializedName("citations")
        @Expose
        private String citations;
        @SerializedName("citations_parsed")
        @Expose
        private List<CitationsParsed> citationsParsed = null;
        @SerializedName("applicant")
        @Expose
        private List<Applicant> applicant = null;

        public List<Inventor> getInventor() {
            return inventor;
        }

        public String getTitle() {
            return title;
        }

        public List<Patentee> getPatentee() {
            return patentee;
        }

        public String getCitations() {
            return citations;
        }

        public List<CitationsParsed> getCitationsParsed() {
            return citationsParsed;
        }

        public List<Applicant> getApplicant() {
            return applicant;
        }


        public class Inventor {
            @SerializedName("name")
            @Expose
            private String name;

            public String getName() {
                return name;
            }
        }

        public class Patentee {
            @SerializedName("name")
            @Expose
            private String name;

            public String getName() {
                return name;
            }
        }

        public class CitationsParsed {
            @SerializedName("doc")
            @Expose
            private Doc doc;
            @SerializedName("text")
            @Expose
            private String text;

            public class Doc {
                @SerializedName("document_number")
                @Expose
                private String documentNumber;
                @SerializedName("kind")
                @Expose
                private String kind;
                @SerializedName("identity")
                @Expose
                private String identity;
                @SerializedName("publication_date")
                @Expose
                private String publicationDate;
                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("publishing_office")
                @Expose
                private String publishingOffice;

                public String getDocumentNumber() {
                    return documentNumber;
                }

                public String getKind() {
                    return kind;
                }

                public String getIdentity() {
                    return identity;
                }

                public String getPublicationDate() {
                    return publicationDate;
                }

                public String getId() {
                    return id;
                }

                public String getPublishingOffice() {
                    return publishingOffice;
                }
            }
        }

        public class Applicant {
            @SerializedName("name")
            @Expose
            private String name;

            public String getName() {
                return name;
            }
        }
    }
}
