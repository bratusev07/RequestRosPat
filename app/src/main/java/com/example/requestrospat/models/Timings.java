package com.example.requestrospat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timings {
    @SerializedName("overall")
    @Expose
    private double overall;
    @SerializedName("search")
    @Expose
    private double search;
    @SerializedName("postprocessing")
    @Expose
    private double postprocessing;
    @SerializedName("preprocessing")
    @Expose
    private double preprocessing;

    public double getOverall() {
        return overall;
    }

    public double getSearch() {
        return search;
    }

    public double getPostprocessing() {
        return postprocessing;
    }

    public double getPreprocessing() {
        return preprocessing;
    }
}
