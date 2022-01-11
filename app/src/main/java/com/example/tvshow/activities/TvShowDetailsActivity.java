package com.example.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshow.R;
import com.example.tvshow.adapters.EpisodesAdapter;
import com.example.tvshow.adapters.ImageSliderAdapter;
import com.example.tvshow.databinding.ActivityTvShowDetailsBinding;
import com.example.tvshow.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.tvshow.pojo.TvShowDetails;
import com.example.tvshow.responses.TvShowDetailsResponse;
import com.example.tvshow.viewModels.TvShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TvShowDetailsActivity extends AppCompatActivity {
    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding; // dataBinding
    private TvShowDetailsViewModel tvShowDetailsViewModel; // viewModel
    private BottomSheetDialog episodeBottomSheetDialog;// bottomSheetDialog
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details); //dataBinding
        doInitialization();

    }

    private void doInitialization() {
        //viewModel
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        // imageBackBtn
        activityTvShowDetailsBinding.imageBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        activityTvShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id", -1));
        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this, tvShowDetailsResponse -> {
            activityTvShowDetailsBinding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails() != null) {
                if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) { // imageSlider
                    loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
                activityTvShowDetailsBinding.setTvShowImageURL(tvShowDetailsResponse.getTvShowDetails().getImagePath());// tvShowImage
                activityTvShowDetailsBinding.setDescription( // description
                        String.valueOf(
                                HtmlCompat.fromHtml(
                                        tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                        )
                );//description

                // read more
                activityTvShowDetailsBinding.textReadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (activityTvShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                            activityTvShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                            activityTvShowDetailsBinding.textReadMore.setText(R.string.read_less);
                            activityTvShowDetailsBinding.textDescription.setEllipsize(null);
                        } else {
                            activityTvShowDetailsBinding.textDescription.setMaxLines(4);
                            activityTvShowDetailsBinding.textReadMore.setText(R.string.read_more);
                            activityTvShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);

                        }
                    }
                });

                // rating
                activityTvShowDetailsBinding.setRating( // to make number double
                        String.format(
                                Locale.getDefault(), "%.2f",
                                Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating()))
                );

                loadBasicTvShowDetails();

                if (tvShowDetailsResponse.getTvShowDetails().getGenres() != null) {
                    activityTvShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres().get(0));
                } else {
                    activityTvShowDetailsBinding.setGenre("N/A");
                }
                activityTvShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + " Min");
                activityTvShowDetailsBinding.buttonWebSite.setOnClickListener(new View.OnClickListener() {//website btn
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                        startActivity(intent);
                    }
                });
                // bottomSheetDialog
                activityTvShowDetailsBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(episodeBottomSheetDialog==null){
                            episodeBottomSheetDialog=new BottomSheetDialog(TvShowDetailsActivity.this);  //bottomSheet
                            layoutEpisodesBottomSheetBinding=DataBindingUtil.                      //dataBinding
                                    inflate(LayoutInflater.from(TvShowDetailsActivity.this),
                                            R.layout.layout_episodes_bottom_sheet,
                                            findViewById(R.id.episode_container),
                                            false);

                            episodeBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                            layoutEpisodesBottomSheetBinding.episodesRecyclerView.
                                    setAdapter(new EpisodesAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes()));
                            layoutEpisodesBottomSheetBinding.textTitle.setText(
                                    String.format("Episodes | %s",getIntent().getStringExtra("name")));
                            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    episodeBottomSheetDialog.dismiss();
                                }
                            });
                        }
                        episodeBottomSheetDialog.show();

                    }
                });


            }
        });
    }

    private void loadImageSlider(List<String> sliderImage) {
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

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
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

    private void setCurrentSliderIndicators(int position) {
        int childCount = activityTvShowDetailsBinding.layoutIndecatorSlider.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTvShowDetailsBinding.layoutIndecatorSlider.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.bachground_slider_indecator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indecator_inactive)
                );
            }
        }


    }

    private void loadBasicTvShowDetails() {
        activityTvShowDetailsBinding.setTvShowName(getIntent().getStringExtra("name"));
        activityTvShowDetailsBinding.setNetworkCountry(getIntent().getStringExtra("network") + " ( " + getIntent().getStringExtra("country") + " )");
        activityTvShowDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activityTvShowDetailsBinding.setStartDate(getIntent().getStringExtra("startDate"));

    }


}