package com.winbee.vasantvalleyschool.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winbee.vasantvalleyschool.Adapter.NotificationAdapter;
import com.winbee.vasantvalleyschool.R;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    TextView textView;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView=view.findViewById(R.id.recyclerViewNotification);
        textView=view.findViewById(R.id.textNotification);


        //todo if their is message disable the textView


    return view;
    }
}