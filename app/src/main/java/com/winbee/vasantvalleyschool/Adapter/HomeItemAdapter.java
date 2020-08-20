package com.winbee.vasantvalleyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.vasantvalleyschool.Models.homeItemModel;
import com.winbee.vasantvalleyschool.R;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    Context context;
    homeItemModel[] homeItemModels;
    OnHomeItemPressed onHomeItemPressed;

    public HomeItemAdapter(Context context, homeItemModel[] homeItemModels, OnHomeItemPressed onHomeItemPressed) {
        this.context = context;
        this.homeItemModels = homeItemModels;
        this.onHomeItemPressed = onHomeItemPressed;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_home_recycler_view,parent,false);

       return new ViewHolder(view,onHomeItemPressed);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(homeItemModels[position].getImage());
        holder.title.setText(homeItemModels[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return homeItemModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView imageView;
        OnHomeItemPressed onHomeItemPressed;
     ;

        public ViewHolder(@NonNull View itemView,OnHomeItemPressed onHomeItemPressed) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_home_items);
            title=itemView.findViewById(R.id.title_home_item);
            this.onHomeItemPressed=onHomeItemPressed;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onHomeItemPressed.onItemPressed(getAdapterPosition());
        }
    }
     public interface OnHomeItemPressed{
        void onItemPressed(int position);
     }

}