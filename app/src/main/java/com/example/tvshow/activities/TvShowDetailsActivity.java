package com.example.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshow.R;
import com.example.tvshow.adapters.ImageSliderAdapter;
import com.example.tvshow.databinding.ActivityTvShowDetailsBinding;
import com.example.tvshow.responses.TvShowDetailsResponse;
import com.example.tvshow.viewModels.TvShowDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class TvShowDetailsActivity extends AppCompatActivity {
    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details); //dataBinding
        doInitialization();

    }

    private void doInitialization() {
        //viewModel
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        activityTvShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id", -1));
        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this, tvShowDetailsResponse -> {
            activityTvShowDetailsBinding.setIsLoading(false);
          if (tvShowDetailsResponse.getTvShowDetails()!=null){
              if (tvShowDetailsResponse.getTvShowDetails().getPictures()!=null){
                  loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
              }
          }
        });
    }

    private void loadImageSlider(List<String> sliderImage){
        activityTvShowDetailsBinding.sliderViewPager.setOffscreenPageLimit((1));
        activityTvShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImage));
        activityTvShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.viewFaddingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImage.size());
        // indicators
        activityTvShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicators(position);
            }
        });


    }
    private void setupSliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0;i<indicators.length;i++){
            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indecator_inactive
            ));

            indicators[i].setLayoutParams(layoutParams);
            activityTvShowDetailsBinding.layoutIndecatorSlider.addView(indicators[i]);
        }
        activityTvShowDetailsBinding.layoutIndecatorSlider.setVisibility(View.VISIBLE);
        setCurrentSliderIndicators(0);

    }

    //currant image slider

    private void setCurrentSliderIndicators(int position){
        int childCount = activityTvShowDetailsBinding.layoutIndecatorSlider.getChildCount();
        for (int i=0; i<childCount;i++){
            ImageView imageView = (ImageView)activityTvShowDetailsBinding.layoutIndecatorSlider.getChildAt(i);
            if (i==position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.bachground_slider_indecator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indecator_inactive)
                );
            }
        }


    }

}