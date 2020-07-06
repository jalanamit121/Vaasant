package com.winbee.vaasant.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.winbee.vaasant.Adapter.HomeworkAdapter;
import com.winbee.vaasant.Adapter.SubmittedAdapter;
import com.winbee.vaasant.Models.AssignmentDatum;
import com.winbee.vaasant.Models.AssignmentToSubmit;
import com.winbee.vaasant.Models.SubmittedAssignment;
import com.winbee.vaasant.Models.SubmittedDatum;
import com.winbee.vaasant.R;
import com.winbee.vaasant.RetrofitApiCall.ApiClient;
import com.winbee.vaasant.Utils.ProgressBarUtil;
import com.winbee.vaasant.Utils.SharedPrefManager;
import com.winbee.vaasant.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitedAssignment extends Fragment {


    RecyclerView recyclerView;
    Button assignmentButton;

    private HomeworkAdapter homeworkAdapter;
//    private ArrayList<AssignmentDatum> list;
    private List<SubmittedDatum> list;
    RelativeLayout home,histroy,logout,today_classes;
    private ProgressBarUtil progressBarUtil;
    ScrollView scrollView;
    private SubmittedAdapter submittedAdapter;
    private RecyclerView assignmentView;
    String Userid;
    private Button btn_submitted;



    public SubmitedAssignment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submited_assignment, container, false);

        recyclerView = view.findViewById(R.id.review);
        assignmentButton = view.findViewById(R.id.AssignmentButton);
        today_classes = view.findViewById(R.id.today_classes);
        Userid = SharedPrefManager.getInstance(getContext()).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(getContext());
        assignmentView = view.findViewById(R.id.assignment_review);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AssignmentToSubmit.class);
                startActivity(intent);

            }
        });
        callAllAssignment(Userid);

        return view;
    }
    private void callAllAssignment(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<SubmittedAssignment> call = apiCAll.getSubmitedAssignment("WB_007",Userid);
        call.enqueue(new Callback<SubmittedAssignment>() {
            @Override
            public void onResponse(Call<SubmittedAssignment> call, Response<SubmittedAssignment> response) {
                SubmittedAssignment submittedAssignment=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getAssignment() == true) {
                        list = new ArrayList<>(Arrays.asList(submittedAssignment.getData()));
                        System.out.println("Suree body: " + response.body());
                        submittedAdapter = new SubmittedAdapter(getContext(), list);
                        assignmentView.setAdapter(submittedAdapter);
                        progressBarUtil.hideProgress();
                    }else
                    {
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                }

                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmittedAssignment> call, Throwable t) {
                Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

}