package com.example.tvshow.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshow.network.ApiClient;
import com.example.tvshow.network.ApiServiceInterface;
import com.example.tvshow.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowRepository {

    private ApiServiceInterface apiServiceInterface;

    public MostPopularTvShowRepository() {
        apiServiceInterface= ApiClient.getRetrofit().create(ApiServiceInterface.class);

    }

    public LiveData<TvShowResponse> getMostPopularTvShow(int page){
        MutableLiveData<TvShowResponse> data= new MutableLiveData<>();
        apiServiceInterface.getMostTvShowPopular(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call,@NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call,@NonNull Throwable t) {
               // Log.d(TAG,t.getMessage());

            }
        });return data;


    }
}
