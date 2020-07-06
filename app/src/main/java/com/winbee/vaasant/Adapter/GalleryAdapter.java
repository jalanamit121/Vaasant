package com.winbee.vaasant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winbee.vaasant.Models.GalleryModel;
import com.winbee.vaasant.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    Context context;
    ArrayList<GalleryModel> galleryModelArrayList;

    public GalleryAdapter(Context context, ArrayList<GalleryModel> galleryModelArrayList) {
        this.context = context;
        this.galleryModelArrayList = galleryModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asDrawable()
                .load(galleryModelArrayList.get(position).getImageurl())
                .error(R.drawable.ic_photo_camera)
                .into(holder.imageView);
        holder.textView.setText(galleryModelArrayList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.galleryImageView);
            textView=itemView.findViewById(R.id.titleGallery);

        }
    }
}
