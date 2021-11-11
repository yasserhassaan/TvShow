package com.example.tvshow.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshow.network.ApiClient;
import com.example.tvshow.network.ApiServiceInterface;
import com.example.tvshow.responses.TvShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {
    private ApiServiceInterface apiServiceInterface;

    public TvShowDetailsRepository() {
        apiServiceInterface= ApiClient.getRetrofit().create(ApiServiceInterface.class);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){

        MutableLiveData<TvShowDetailsResponse> data =new MutableLiveData<>();
        apiServiceInterface.getTvShowDetails(tvShowId).enqueue(new Callback<TvShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailsResponse> call,@NonNull Response<TvShowDetailsResponse> response) {
                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);

            }
        });return data;

    }
}
