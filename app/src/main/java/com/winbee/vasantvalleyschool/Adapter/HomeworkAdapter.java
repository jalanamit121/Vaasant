package com.winbee.vasantvalleyschool.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.vasantvalleyschool.Models.AssignmentDatum;
import com.winbee.vasantvalleyschool.R;

import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    Context context;
    private ArrayList<AssignmentDatum> assignmentDatumList;
    OnHOmeworkClicked onHOmeworkClicked;

    public HomeworkAdapter(Context context, ArrayList<AssignmentDatum> assignmentDatumList, OnHOmeworkClicked onHOmeworkClicked) {
        this.context = context;
        this.assignmentDatumList = assignmentDatumList;
        this.onHOmeworkClicked = onHOmeworkClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_homework, parent, false);
        Log.d("TAG", "onCreateViewHolder: "+assignmentDatumList);
        return new ViewHolder(view,onHOmeworkClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//   ;     final AssignmentDatum assignmentDatum = assignmentDatumList.get(position);
////        holder.title.setText(assignmentDatum.getSubject());
////        holder.subject.setText(assignmentDatum.getTopic());
////        holder.date.setText(assignmentDatum.getDeadline_date())

        holder.title.setText(assignmentDatumList.get(position).getTopic());
        holder.subject.setText(assignmentDatumList.get(position).getSubject());
        holder.date.setText(assignmentDatumList.get(position).getStart_date());


        Log.d("TAG", "onBindViewHolder: "+assignmentDatumList);

    }

    @Override
    public int getItemCount() {
       // return assignmentDatumList == null ? 0 : assignmentDatumList.size();
        return  assignmentDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, subject, date;
        OnHOmeworkClicked onHOmeworkClicked;

        public ViewHolder(@NonNull View itemView,OnHOmeworkClicked onHOmeworkClicked) {
            super(itemView);
            title = itemView.findViewById(R.id.titleHomework);
            subject = itemView.findViewById(R.id.subjectHomework);
            date = itemView.findViewById(R.id.dateHomework);
            this.onHOmeworkClicked=onHOmeworkClicked;


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onHOmeworkClicked.onHomeworkClicked(getAdapterPosition());
        }
    }
    public interface  OnHOmeworkClicked{
        void onHomeworkClicked(int position);
    }
}
