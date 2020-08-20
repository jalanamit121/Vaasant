package com.winbee.vasantvalleyschool.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.vasantvalleyschool.Adapter.ClassAdapter;
import com.winbee.vasantvalleyschool.Models.CourseDatum;
import com.winbee.vasantvalleyschool.Models.PurchasedMainModel;
import com.winbee.vasantvalleyschool.R;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyClassFragment extends Fragment {
    String UserId;
    private ClassAdapter adapter;
    private RecyclerView video_list_recycler;
    private List<CourseDatum> list;
    private ProgressBarUtil progressBarUtil;
    TextView nocourse;


    public MyClassFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_class, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        video_list_recycler = getActivity().findViewById(R.id.gec_home_recycle);
        progressBarUtil   =  new ProgressBarUtil(getActivity());
        nocourse=getActivity().findViewById(R.id.nocourse);
        ClassAdapter myAdapter = new ClassAdapter(getActivity(),list);
        video_list_recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        video_list_recycler.setAdapter(myAdapter);
        UserId=SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
        callApiService(UserId);

    }
    private void callApiService(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<PurchasedMainModel> call = apiCAll.getCourseById(1,Userid,"WB_007");
        call.enqueue(new Callback<PurchasedMainModel>() {
            @Override
            public void onResponse(Call<PurchasedMainModel> call, Response<PurchasedMainModel> response) {
                PurchasedMainModel purchasedMainModel=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getPurchased() == true) {
                        //courses.setVisibility(View.VISIBLE);
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(purchasedMainModel).getData()));
                        System.out.println("Suree body: " + response.body());
                        adapter = new ClassAdapter(getActivity(), list);
                        video_list_recycler.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        nocourse.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                }
                else{
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getActivity(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchasedMainModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }
}