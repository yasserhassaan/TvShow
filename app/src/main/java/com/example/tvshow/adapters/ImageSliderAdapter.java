package com.example.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshow.R;
import com.example.tvshow.databinding.ItemContainerSliderImageBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private List<String> sliderImage ;

    //private String [] sliderImage;
    private LayoutInflater layoutInflater;

    //constructor

    public ImageSliderAdapter(List<String> sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater=layoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding sliderImageBinding= DataBindingUtil
                .inflate(layoutInflater, R.layout.item_container_slider_image,parent,false);

        return new ImageSliderViewHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSlderImage(sliderImage.get(position));

    }

    @Override
    public int getItemCount() {
        return sliderImage.size();
    }



    public class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerSliderImageBinding itemContainerSliderImageBinding; //dataBinding

        public ImageSliderViewHolder( ItemContainerSliderImageBinding itemContainerSliderImageBinding) {//constructor
            super(itemContainerSliderImageBinding.getRoot());
            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding;
        }

        public void bindSlderImage(String imageURL){
            itemContainerSliderImageBinding.setImageURL(imageURL);

        }
    }
}
