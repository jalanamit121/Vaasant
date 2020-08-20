package com.winbee.vasantvalleyschool;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.vasantvalleyschool.Adapter.SemesterTopicAdapter;
import com.winbee.vasantvalleyschool.Models.SemesterName;
import com.winbee.vasantvalleyschool.Models.UrlName;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GecSemesterTopicActivity extends AppCompatActivity {
    private SemesterName semester_data;
    private List<UrlName> list;
    private RecyclerView video_list_recycler;
    private SemesterTopicAdapter adapter;
    RelativeLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    String UserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gec_semester_topic);
        video_list_recycler = findViewById(R.id.gec_semester_topic_recycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        UserID= SharedPrefManager.getInstance(this).refCode().getUserId();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            semester_data = (SemesterName)bundle.getSerializable("semester_name");
            callTopicApiService();

        }

    }
    private void callTopicApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UrlName>> call = mService.getTopic(2,semester_data.getBucket_ID(),semester_data.getChild_Link(),UserID);
        call.enqueue(new Callback<ArrayList<UrlName>>() {
            @Override
            public void onResponse(Call<ArrayList<UrlName>> call, Response<ArrayList<UrlName>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new SemesterTopicAdapter(GecSemesterTopicActivity.this,list);
                    video_list_recycler.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UrlName>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topic_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.topic_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint("Search Topic");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTopicByName(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTopicByName(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchTopicByName(String s) {
        List<UrlName> filteredList = new ArrayList<>();
        try{
            for(int i=0;i<list.size();i++) {
                String subjectName = "", topic="", type="" ;

                if(list.get (i).getSubject()!=null || list.get (i).getTopic()!=null  ){
                    subjectName= list.get (i).getSubject();
                    topic= list.get (i).getTopic();
                    //type= list.get(i).getType();
                }

                if(subjectName.toLowerCase().contains(s.toLowerCase()) || topic.toLowerCase().contains(s.toLowerCase()) ) {
                    filteredList.add(list.get(i));
                }
            }

            adapter = new SemesterTopicAdapter (GecSemesterTopicActivity.this, filteredList);
            video_list_recycler.setAdapter (adapter);
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
