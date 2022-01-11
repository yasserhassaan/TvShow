package com.example.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshow.R;
import com.example.tvshow.databinding.ItemContainerEpisodesBinding;
import com.example.tvshow.databinding.ItemContainerTvShowBinding;
import com.example.tvshow.pojo.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {
    //private ArrayList<EpisodeModel> movieList = new ArrayList<>();
    private List<EpisodeModel> episodeModelsList;
    private LayoutInflater layoutInflater;


    public EpisodesAdapter(List<EpisodeModel> episodeModelsList) {//constructor
        this.episodeModelsList = episodeModelsList;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodesBinding itemContainerEpisodesBinding= DataBindingUtil
                .inflate(layoutInflater, R.layout.item_container_episodes,parent,false);
        return new EpisodesViewHolder(itemContainerEpisodesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        holder.bindEpisodes(episodeModelsList.get(position));

    }

    @Override
    public int getItemCount() {
        return episodeModelsList.size();
    }


    public class EpisodesViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerEpisodesBinding item_container_episodesBinding;

        // view
        public EpisodesViewHolder(ItemContainerEpisodesBinding item_container_episodes) {
            super(item_container_episodes.getRoot());
            this.item_container_episodesBinding=item_container_episodes;
            //view
        }
        private void bindEpisodes(EpisodeModel episodeModel){
            String title= "S";
            String season= episodeModel.getSeason().toString();
            if (season.length()==1){
                season="0".concat(season);        // .concat use to add two string together or more string
            }
            String episodeNumber=episodeModel.getEpisode().toString();
            if (episodeNumber.length()==1){
                episodeNumber="0".concat(episodeNumber);
            }
            episodeNumber="E".concat(episodeNumber);
            title=title.concat(season).concat(episodeNumber);

            item_container_episodesBinding.setTitle(title);
            item_container_episodesBinding.setName(episodeModel.getName());
            item_container_episodesBinding.setAirDate(episodeModel.getAirDate());

        }
    }
}
