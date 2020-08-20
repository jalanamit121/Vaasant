package com.winbee.vasantvalleyschool.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winbee.vasantvalleyschool.Adapter.GalleryAdapter;
import com.winbee.vasantvalleyschool.Models.GalleryModel;
import com.winbee.vasantvalleyschool.R;

import java.util.ArrayList;

public class GalleryFragement extends Fragment {

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    ArrayList<GalleryModel> galleryModelArrayList;




    public GalleryFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_gallery_fragement, container, false);

        recyclerView=v.findViewById(R.id.recyclerViewGallery);
        galleryModelArrayList=new ArrayList<>();

        getDataInArray();
        return  v;
    }

    private void getDataInArray() {
        //todo get data of array here and pass on this method
//done b
// //fee wala v
// oss
        setDataToRecyclerView(galleryModelArrayList);
    }

    private void setDataToRecyclerView(ArrayList<GalleryModel> galleryModelArrayList) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        galleryAdapter=new GalleryAdapter(getContext(), galleryModelArrayList);
        recyclerView.setAdapter(galleryAdapter);

    }
}