package com.winbee.vaasant.Ui;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.vaasant.Adapter.HomeworkAdapter;
import com.winbee.vaasant.Models.AssignmentDatum;
import com.winbee.vaasant.Models.AssignmentToSubmit;
import com.winbee.vaasant.Models.HomeworkModel;
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



public class HomeworkFragment extends Fragment implements HomeworkAdapter.OnHOmeworkClicked {


    private HomeworkAdapter homeworkAdapter;
    private ArrayList<AssignmentDatum> list;
    private ProgressBarUtil progressBarUtil;
    private RecyclerView assignmentView;
    String Userid;
    RelativeLayout today_classes;

    ArrayList<HomeworkModel> homeworkModelArrayList;

    Button subittedAssignment;


    public HomeworkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homework, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        assignmentView=getActivity().findViewById(R.id.recycler_view_homework);
        today_classes=getActivity().findViewById(R.id.today_classes);
        subittedAssignment=view.findViewById(R.id.submittedAssignmentBtn);
        progressBarUtil   =  new ProgressBarUtil(getActivity());
        Userid= SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
        callAllAssignment(Userid);
    }

    private void callAllAssignment(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<AssignmentToSubmit> call = apiCAll.getAllAssignment("WB_007",Userid);
        call.enqueue(new Callback<AssignmentToSubmit>() {
            @Override
            public void onResponse(Call<AssignmentToSubmit> call, Response<AssignmentToSubmit> response) {
                AssignmentToSubmit assignmentToSubmit=response.body();
                int statusCode = response.code();
                list = new ArrayList<>();
                if(statusCode==200) {
                    if (response.body().getAssignment() == 1) {
                        list = new ArrayList<>(Arrays.asList(assignmentToSubmit.getData()));
                        System.out.println("Suree body: " + response.body());


                        homeworkAdapter = new HomeworkAdapter(getContext(), list, HomeworkFragment.this);
                        assignmentView.setHasFixedSize(true);
                        assignmentView.setLayoutManager(new LinearLayoutManager(getContext()));
                        assignmentView.setAdapter(homeworkAdapter);
                        progressBarUtil.hideProgress();
                        Log.d("TAG", "onResponse: "+list);
                        Log.d("TAG", "onResponse: "+list.size());
                    }else{
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getActivity(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssignmentToSubmit> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });

        subittedAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(),SubmitedAssignment.class));
                SubmitedAssignment submitedAssignment = new SubmitedAssignment();
                FragmentTransaction fragmentTransaction= null;
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,submitedAssignment,"SubmitedAssignment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

    }

    @Override
    public void onHomeworkClicked(int position) {

        assignmentToSubmit assignmentToSubmit= new assignmentToSubmit();
        FragmentTransaction fragmentTransaction= null;
        if (getFragmentManager() != null) {
            fragmentTransaction = getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrame,assignmentToSubmit,"HomeworkDiscriptionFragment");

            Bundle bundleHomework= new Bundle();
            bundleHomework.putString("title",list.get(position).getTopic());
            bundleHomework.putString("subject",list.get(position).getSubject());
            bundleHomework.putString("date",list.get(position).getStart_date());
            bundleHomework.putString("pdfUrl",list.get(position).getContent_name());
            bundleHomework.putString("bucketId",list.get(position).getBucketId());
            bundleHomework.putString("assignmentId",list.get(position).getAssignment_id());
            assignmentToSubmit.setArguments(bundleHomework);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();



        }
//done boss phle jesa hogya
        // okk
        //
    }
}


