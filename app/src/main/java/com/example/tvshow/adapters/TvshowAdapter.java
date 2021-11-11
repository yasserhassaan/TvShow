package com.example.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshow.R;
import com.example.tvshow.databinding.ItemContainerTvShowBinding;
import com.example.tvshow.listeners.TvShowListener;
import com.example.tvshow.pojo.TvShowModel;

import java.util.ArrayList;
import java.util.List;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.TvShowViewHolder> {

    private List<TvShowModel> tvShow ;
    private LayoutInflater layoutInflater;
    private TvShowListener tvShowListener;


    public TvshowAdapter(List<TvShowModel> tvShow,TvShowListener tvShowListener) {    //constructor
        this.tvShow = tvShow;
        this.tvShowListener=tvShowListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding= DataBindingUtil
                .inflate(layoutInflater,R.layout.item_container_tv_show,parent,false);

        return new TvShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bindTvShow(tvShow.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShow.size();
    }

    public void setList(ArrayList<TvShowModel> tvShow) {
        this.tvShow = tvShow;
        notifyDataSetChanged();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding; //dataBinding

        // view
        public TvShowViewHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTvShow(TvShowModel tvShowModel) { //use in onBindingViewHolder
            itemContainerTvShowBinding.setTvShow(tvShowModel); //from xml
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tvShowListener.onTvShowClicked(tvShowModel);
                }
            });


        }
    }
}
