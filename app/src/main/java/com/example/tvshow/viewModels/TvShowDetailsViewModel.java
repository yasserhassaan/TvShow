package com.example.tvshow.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshow.repositories.TvShowDetailsRepository;
import com.example.tvshow.responses.TvShowDetailsResponse;

public class TvShowDetailsViewModel  extends ViewModel {
    private TvShowDetailsRepository tvShowDetailsRepository;

    public TvShowDetailsViewModel() {
        tvShowDetailsRepository= new TvShowDetailsRepository();
    }
    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){
     return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }
}
