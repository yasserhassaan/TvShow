package com.example.tvshow.network;

import com.example.tvshow.responses.TvShowDetailsResponse;
import com.example.tvshow.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceInterface {

    @GET("most-popular")
    Call<TvShowResponse> getMostTvShowPopular(@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowDetails(@Query("q") String tvShowId);
}
