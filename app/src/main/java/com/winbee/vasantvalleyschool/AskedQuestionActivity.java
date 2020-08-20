package com.winbee.vasantvalleyschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.vasantvalleyschool.Adapter.AskedQuestionAdapter;
import com.winbee.vasantvalleyschool.Models.UrlQuestion;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskedQuestionActivity extends AppCompatActivity {
    RelativeLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    private AskedQuestionAdapter adapter;
    private ArrayList<UrlQuestion> list;
    private RecyclerView askedQuestion;
    ImageView btmNewQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_question);
        askedQuestion = findViewById(R.id.gec_asked_question_recycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        btmNewQuestion=findViewById(R.id.btmNewQuestion);

       final String documentID=getIntent().getStringExtra("documentID");
        if(documentID!=null)
            callAskedQuestionApiService(documentID);
        System.out.println("intent "+documentID);

        btmNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskedQuestionActivity.this,AskedNewQuestionActivity.class);
                intent.putExtra("documentID",documentID);
                startActivity(intent);
            }
        });

    }

    private void callAskedQuestionApiService(String documentID){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UrlQuestion>> call =apiCall.getQuestion(documentID);
        call.enqueue(new Callback<ArrayList<UrlQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<UrlQuestion>> call, Response<ArrayList<UrlQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AskedQuestionAdapter(AskedQuestionActivity.this,list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UrlQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

}
