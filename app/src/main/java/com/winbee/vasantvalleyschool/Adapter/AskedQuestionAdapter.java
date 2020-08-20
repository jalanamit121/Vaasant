package com.winbee.vasantvalleyschool.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.vasantvalleyschool.AskedSolutionActivity;
import com.winbee.vasantvalleyschool.Models.UrlQuestion;
import com.winbee.vasantvalleyschool.R;

import java.util.ArrayList;

public class AskedQuestionAdapter extends RecyclerView.Adapter<AskedQuestionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UrlQuestion> list;

    public AskedQuestionAdapter(Context context, ArrayList<UrlQuestion> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AskedQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.askedquestionadapter,parent, false);
        return  new AskedQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AskedQuestionAdapter.ViewHolder holder, final int position) {
        holder.text_question.setText(list.get(position).getFile_name_to_show());


        holder.branch_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, AskedSolutionActivity.class);
                bundle.putSerializable("file_name",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_question;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_question = itemView.findViewById(R.id.text_question);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}



