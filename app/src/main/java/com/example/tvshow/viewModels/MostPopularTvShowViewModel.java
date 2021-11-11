package com.example.tvshow.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshow.repositories.MostPopularTvShowRepository;
import com.example.tvshow.responses.TvShowResponse;

public class MostPopularTvShowViewModel extends ViewModel {
    private MostPopularTvShowRepository mostPopularTvShowRepository;

    public MostPopularTvShowViewModel() {
        mostPopularTvShowRepository=new MostPopularTvShowRepository();
    }

    public LiveData<TvShowResponse> getMostPopularTvShow(int page){

        return mostPopularTvShowRepository.getMostPopularTvShow(page);   // return MutableLiveData<TvShowResponse> data= new MutableLiveData<>();

    }
}
