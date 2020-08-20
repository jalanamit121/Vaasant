package com.winbee.vasantvalleyschool.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.vasantvalleyschool.GecYouTubeActivity;
import com.winbee.vasantvalleyschool.Models.LiveClassModel;
import com.winbee.vasantvalleyschool.R;
import com.winbee.vasantvalleyschool.Utils.AssignmentData;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class AllLiveClassAdapter extends RecyclerView.Adapter<AllLiveClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LiveClassModel> list;

    public AllLiveClassAdapter(Context context, ArrayList<LiveClassModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllLiveClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_all_live_classes,parent, false);
        return  new AllLiveClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllLiveClassAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.live_topic.setText(list.get(position).getTopic());
        holder.live_subject.setText(list.get(position).getSubject());
        holder.live_teacher.setText(list.get(position).getTeacher());
        holder.time.setText(list.get(position).getStart_Time());
        holder.video_started.setText(list.get(position).getCS_type_name());
        if (list.get(position).getCS_type_code().equals(1)){
            holder.video_started.setText("Live");
            holder.video_started.setVisibility(View.VISIBLE);
            holder.image_gif.setVisibility(View.VISIBLE);
        } else if (list.get(position).getCS_type_code().equals(2)) {
            holder.video_started.setText("Completed");
            holder.video_started.setVisibility(View.VISIBLE);
            holder.image_gif.setVisibility(View.GONE);
        }else if (list.get(position).getCS_type_code().equals(0)){
            holder.video_started.setText("Scheduled");
            holder.video_started.setVisibility(View.VISIBLE);
            holder.image_gif.setVisibility(View.GONE);

        }


        holder.branch_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getCS_type_code().equals(1)) {
                    AssignmentData.LiveId=list.get(position).getContentLink();
                    //Intent intent = new Intent(context, GecYouTubeActivity.class);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list.get(position).getContentLink()));
                    context.startActivity(intent);
                } else if (list.get(position).getCS_type_code().equals(0)) {
                    Toast.makeText(context, "Class Not Started yet", Toast.LENGTH_SHORT).show();
                } else if (list.get(position).getCS_type_code().equals(2)){
                    Toast.makeText(context, "Class Completed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Error Occur,Contact Support", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView live_topic,live_subject,live_teacher,time,video_started;
        private RelativeLayout branch_live;
        GifImageView image_gif;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            live_topic = itemView.findViewById(R.id.live_topic);
            live_subject = itemView.findViewById(R.id.live_subject);
            live_teacher = itemView.findViewById(R.id.live_teacher);
            time = itemView.findViewById(R.id.time);
            video_started = itemView.findViewById(R.id.video_started);
            branch_live = itemView.findViewById(R.id.branch_live);
            image_gif = itemView.findViewById(R.id.image_gif);
        }
    }
}
