package com.example.tvshow.responses;

import com.example.tvshow.pojo.TvShowModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("tv_shows")
    @Expose
    private List<TvShowModel> tvShowModels = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<TvShowModel> getTvShowModels() {
        return tvShowModels;
    }

    public void setTvShowModels(List<TvShowModel> tvShowModels) {
        this.tvShowModels = tvShowModels;
    }
}
