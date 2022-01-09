package com.example.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmexm1.R;
import com.example.tvshow.databinding.ItemContainerEpisodesBinding;
import com.example.tvshow.databinding.ItemContainerTvShowBinding;
import com.example.tvshow.pojo.EpisodeModel;

import java.util.ArrayList;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {
    //private ArrayList<EpisodeModel> movieList = new ArrayList<>();


    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_episodes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setList(ArrayList<EpisodeModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }



    public class EpisodesViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerEpisodesBinding item_container_episodes;


        // view
        public EpisodesViewHolder(ItemContainerEpisodesBinding item_container_episodes) {
            super(item_container_episodes.getRoot());
            //view
        }
    }
}
