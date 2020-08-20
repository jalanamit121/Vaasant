package com.winbee.vasantvalleyschool.Ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.vasantvalleyschool.Adapter.HomeItemAdapter;
import com.winbee.vasantvalleyschool.Models.AttendenceModel;
import com.winbee.vasantvalleyschool.Models.homeItemModel;
import com.winbee.vasantvalleyschool.R;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.AssignmentData;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements HomeItemAdapter.OnHomeItemPressed {

    ImageView profileImageHome;
    TextView nameHome;
    RecyclerView recycler_view_home;


    homeItemModel[] homeItemModels;
    HomeItemAdapter homeItemAdapter;
    String Name,Userid;
    private ProgressBarUtil progressBarUtil;

    View view;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        profileImageHome = view.findViewById(R.id.profile_image_home);
        progressBarUtil   =  new ProgressBarUtil(getActivity());
        Name= SharedPrefManager.getInstance(getActivity()).refCode().getName();
        Userid= SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
        nameHome = view.findViewById(R.id.profile_name_home);
        nameHome.setText(Name);

        recycler_view_home = view.findViewById(R.id.recycler_view_home);


        addingDatatoArray();


        return view;
    }

    private void addingDatatoArray() {

        homeItemModels = new homeItemModel[]{
                new homeItemModel(R.drawable.person, "My Profile"),
                new homeItemModel(R.drawable.teacher, "My Class"),
                new homeItemModel(R.drawable.ic_class, "Live Class"),
                new homeItemModel(R.drawable.study, "Homework"),
                new homeItemModel(R.drawable.attendance, "Attendance"),
                new homeItemModel(R.drawable.exam, "Test"),
                new homeItemModel(R.drawable.fee, "Fee"),
                new homeItemModel(R.drawable.message, "Message"),
                new homeItemModel(R.drawable.gallery, "Gallery")

        };
        addingDatatoRecyclerView(homeItemModels);
    }

    private void addingDatatoRecyclerView(homeItemModel[] homeItemModel) {
        Log.d("TAG", "addingDatatoRecyclerView: " + homeItemModel);
        recycler_view_home.setHasFixedSize(true);
        recycler_view_home.setLayoutManager(new GridLayoutManager(getContext(), 3));
        homeItemAdapter = new HomeItemAdapter(getContext(), homeItemModel, this);
        recycler_view_home.setAdapter(homeItemAdapter);
    }

    @Override
    public void onItemPressed(int position) {

        switch (homeItemModels[position].getTitle()) {

            case "My Profile":
            //    Toast.makeText(getContext(), "My Profile pressed", Toast.LENGTH_SHORT).show();
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                FragmentTransaction fragmentTransaction= null;
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,myProfileFragment,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("My Profile");

                break;

            case "My Class":
           //     Toast.makeText(getContext(), "My Class pressed", Toast.LENGTH_SHORT).show();
                MyClassFragment myClassFragment = new MyClassFragment();
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,myClassFragment,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("My Class");

                break;

            case "Homework":
             //   Toast.makeText(getContext(), "Homework pressed", Toast.LENGTH_SHORT).show();
                HomeworkFragment homeworkFragment = new HomeworkFragment();
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,homeworkFragment,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("Homework");
                break;

            case "Attendance":
                callAttendenceService();
                break;

            case "Test":
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                Home.titleHome.setText("Test");
                break;

            case "Fee":
                FeeFragment feeFragment = new FeeFragment();
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,feeFragment,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("Fee");
                break;

            case "Message":
                MessageFragment messageFragment = new MessageFragment();
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,messageFragment,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("Message");
                break;

            case "Gallery":
                GalleryFragement galleryFragement = new GalleryFragement();
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,galleryFragement,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("Gallery");
                break;

            case "Live Class":
                LiveClassFragment liveClassFragment = new LiveClassFragment();
                if (getFragmentManager() != null) {
                    fragmentTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.homeFrame,liveClassFragment,"MyProfileFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Home.titleHome.setText("Live Class");
                break;


        }


    }
    private void callAttendenceService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<AttendenceModel> call = apiCAll.fetchAttendence(Userid);
        call.enqueue(new Callback<AttendenceModel>() {
            @Override
            public void onResponse(Call<AttendenceModel> call, Response<AttendenceModel> response) {
                int statusCode = response.code();
                //liveList = new ArrayList();
                if(statusCode==200) {
                    AssignmentData.AttendenceMessage=response.body().getMessage();
                    updateAttendenceDialog();
                    progressBarUtil.hideProgress();
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getActivity(),"Network Issues" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AttendenceModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    private void updateAttendenceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(AssignmentData.AttendenceMessage);
        builder.setCancelable(false);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }
}