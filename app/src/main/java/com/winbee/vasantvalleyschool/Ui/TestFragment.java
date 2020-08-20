package com.winbee.vasantvalleyschool.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.winbee.vasantvalleyschool.Adapter.SubjectAdapter;
import com.winbee.vasantvalleyschool.Models.SectionDetailsDataModel;
import com.winbee.vasantvalleyschool.Models.SectionDetailsMainModel;
import com.winbee.vasantvalleyschool.R;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.OnlineTestData;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestFragment extends Fragment {
    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_subject;
    private Toast toast_msg;
    String UserId;


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        iniIDs();
        getSubjectName();
    }
    private void iniIDs(){
        shimmerLayout=getActivity().findViewById(R.id.shimmerLayout);
        recycle_subject=getActivity().findViewById(R.id.recycle_subject);
        UserId= SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
    }
    private void getSubjectName() {
        apiCall();
        ClientApi apiClient= ApiClient.getClient().create(ClientApi.class);
        Call<SectionDetailsMainModel> call=apiClient.fetchSectionDetails(OnlineTestData.org_code,OnlineTestData.auth_code,UserId);
        call.enqueue(new Callback<SectionDetailsMainModel>() {
            @Override
            public void onResponse(Call<SectionDetailsMainModel> call, Response<SectionDetailsMainModel> response) {
                apiCalled();
                SectionDetailsMainModel sectionDetailsMainModel=response.body();
                if(sectionDetailsMainModel!=null){
                    if (sectionDetailsMainModel.getMessage().equalsIgnoreCase("TRUE")){
                        List<SectionDetailsDataModel> sectionDetailsDataModelList=new ArrayList<>(Arrays.asList(sectionDetailsMainModel.getData()));
                        SubjectAdapter subjectAdapter=new SubjectAdapter(getActivity(),sectionDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recycle_subject.setLayoutManager(layoutManager);
                        recycle_subject.setItemAnimator(new DefaultItemAnimator());
                        recycle_subject.setAdapter(subjectAdapter);
                    }
                    else
                        doToast(sectionDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SectionDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }

}