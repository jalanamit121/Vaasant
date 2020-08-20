package com.winbee.vasantvalleyschool;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.vasantvalleyschool.Adapter.SemesterAdapter;
import com.winbee.vasantvalleyschool.Models.CourseDatum;
import com.winbee.vasantvalleyschool.Models.SemesterName;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCourseSubjectActivity extends AppCompatActivity {
    private CourseDatum courseDatum;
    private ArrayList<SemesterName> list;
    private RecyclerView video_list_recycler;
    private SemesterAdapter adapter;
    RelativeLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_subject);
        video_list_recycler = findViewById(R.id.gec_semester_recycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            courseDatum = (CourseDatum) bundle.getSerializable("my_course");
            callCourseSubjectApiService();

        }


    }

    private void callCourseSubjectApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<SemesterName>> call = mService.getCourseSubject(1,"WB_007",courseDatum.getChild_Link(),UserId);
        call.enqueue(new Callback<ArrayList<SemesterName>>() {
            @Override
            public void onResponse(Call<ArrayList<SemesterName>> call, Response<ArrayList<SemesterName>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new SemesterAdapter(MyCourseSubjectActivity.this,list);
                    video_list_recycler.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SemesterName>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }

}
