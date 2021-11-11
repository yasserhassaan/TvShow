package com.example.tvshow.responses;

import com.example.tvshow.pojo.TvShowDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailsResponse  {

    @SerializedName("tvShow")
    @Expose
    private TvShowDetails tvShowDetails;

    public TvShowDetails getTvShowDetails() {
        return tvShowDetails;
    }

    public void setTvShowDetails(TvShowDetails tvShowDetails) {
        this.tvShowDetails = tvShowDetails;
    }
}
