package com.winbee.vaasant.Ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.winbee.vaasant.Models.SubmitAssignment;
import com.winbee.vaasant.R;
import com.winbee.vaasant.RetrofitApiCall.ApiClient;
import com.winbee.vaasant.Utils.ProgressBarUtil;
import com.winbee.vaasant.Utils.SharedPrefManager;
import com.winbee.vaasant.WebApi.ClientApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class SubmitAssignmentHomework extends Fragment {
    private EditText description;
    private Button uploadButton,addImageButton;
    private  static final int IMG_REQUEST=777;
    private Bitmap bitmap;
    ImageView image_view;
    private ProgressBarUtil progressBarUtil;
    String UserId;
    String bucketId;
    String AssisgnmentId;
    public SubmitAssignmentHomework() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_submit_assignment, container, false);
        description = view.findViewById(R.id.description);
        addImageButton = view.findViewById(R.id.addImageButton);
        uploadButton = view.findViewById(R.id.uploadButton);
        image_view = view.findViewById(R.id.image_view);
        progressBarUtil = new ProgressBarUtil(getContext());
        UserId= SharedPrefManager.getInstance(getActivity()).refCode().getUserId();

        getBundle();

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                fileValidation();
            }
        });

        return view;
    }

    private void getBundle() {
        Bundle bundle=getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String subject = bundle.getString("subject");
            String date = bundle.getString("date");
            String pdfUrl = bundle.getString("pdfUrl");
            bucketId = bundle.getString("bucketId");
            AssisgnmentId = bundle.getString("assignmentId");

            Log.d("TAG", "getBundle: "+AssisgnmentId+" "+bucketId+" "+description.getText().toString());

        }
        }

    private void fileValidation() {
        String Description = description.getText().toString();
        String Image =imageToString();
        String Doc="photo";


        //validating inputs
        if (TextUtils.isEmpty(Description)) {
            description.setError("Please enter your answer");
            description.requestFocus();
            return;
        }

        callSubmitAssignment(Description,Image);
    }
    private void callSubmitAssignment(String Description,String Image){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<SubmitAssignment> call = mService.getSubmitAssignment("WB_007",UserId, bucketId,AssisgnmentId,Image,Description,"photo");
        Log.d("TAG", "onBindViewHolder: "+UserId+bucketId+AssisgnmentId+Image+Description);
        call.enqueue(new Callback<SubmitAssignment>() {
            @Override
            public void onResponse(Call<SubmitAssignment> call, Response<SubmitAssignment> response) {
                int statusCode  = response.code();
                if(statusCode==200  ) {
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(getActivity(),Home.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Submit Successful", Toast.LENGTH_SHORT).show();
                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(getContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SubmitAssignment> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), selectedImage);

                image_view.setImageBitmap(bitmap);

                Toast.makeText(getActivity(), "File Selected", Toast.LENGTH_SHORT).show();
                addImageButton.setVisibility(View.GONE);
                uploadButton.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String imageToString()
    {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Log.d("ll", "imageToString:   "+ Base64.encodeToString(byteArray, Base64.DEFAULT));
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}