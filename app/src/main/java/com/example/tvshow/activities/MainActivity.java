package com.example.tvshow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvshow.R;
import com.example.tvshow.adapters.TvshowAdapter;
import com.example.tvshow.databinding.ActivityMainBinding;
import com.example.tvshow.listeners.TvShowListener;
import com.example.tvshow.pojo.TvShowModel;
import com.example.tvshow.responses.TvShowResponse;
import com.example.tvshow.viewModels.MostPopularTvShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {
    private MostPopularTvShowViewModel mostPopularTvShowViewModel; // viewModel
    private ActivityMainBinding activityMainBinding;  //dataBinding
    private List<TvShowModel> tvShowModelList = new ArrayList<>();
    private TvshowAdapter tvshowAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main); //dataBinding
        doInitialization();


    }

    private void doInitialization() {
        activityMainBinding.tvShowRecyclerview.setHasFixedSize(true);
        // viewModel
        mostPopularTvShowViewModel = new ViewModelProvider(this).get(MostPopularTvShowViewModel.class);
        tvshowAdapter = new TvshowAdapter(tvShowModelList,this);
        activityMainBinding.tvShowRecyclerview.setAdapter(tvshowAdapter);
        activityMainBinding.tvShowRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowRecyclerview.canScrollVertically(1)) { // ! convert false to true
                    if (currentPage <= totalAvailablePage) {
                        currentPage += 1;
                        getMostPopularTvShow();
                    }
                }
            }
        });
        getMostPopularTvShow();

    }

    private void getMostPopularTvShow() {
        // activityMainBinding.setIsLoading(true);
        toggleLoading();
        mostPopularTvShowViewModel.getMostPopularTvShow(currentPage)
                .observe(this, mostPopularTvShowViewResponse ->  // TvShowResponse object
                        {
                            // activityMainBinding.setIsLoading(false); // for progressBar
                            toggleLoading();
                            if (mostPopularTvShowViewModel != null) {
                                totalAvailablePage = mostPopularTvShowViewResponse.getPages(); // total pages

                                if (mostPopularTvShowViewResponse.getTvShowModels() != null) {
                                    int oldCount = tvShowModelList.size();
                                    tvShowModelList.addAll(mostPopularTvShowViewResponse.getTvShowModels());
                                    //tvshowAdapter.notifyDataSetChanged();
                                    tvshowAdapter.notifyItemRangeInserted(oldCount, tvShowModelList.size());
                                }
                            }
                        }
                );
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }


    }

    @Override
    public void onTvShowClicked(TvShowModel tvShowModel) {
        Intent intent=new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("id",tvShowModel.getId());
        intent.putExtra("name",tvShowModel.getName());
        intent.putExtra("startDate",tvShowModel.getStartDate());
        intent.putExtra("country",tvShowModel.getCountry());
        intent.putExtra("network",tvShowModel.getNetwork());
        intent.putExtra("status",tvShowModel.getStatus());
        startActivity(intent);


    }
}











/*mostPopularTvShowViewModel.getMostPopularTvShow(0).observe(this, new Observer<TvShowResponse>() {
@Override public void onChanged(TvShowResponse tvShowResponse) {
Toast.makeText(MainActivity.this, "total pages"+tvShowResponse.getPages(), Toast.LENGTH_SHORT).show();

}
}); */
