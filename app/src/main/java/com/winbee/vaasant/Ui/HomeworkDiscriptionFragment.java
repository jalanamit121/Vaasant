package com.winbee.vaasant.Ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.vaasant.Models.SubmitAssignment;
import com.winbee.vaasant.R;
import com.winbee.vaasant.RetrofitApiCall.ApiClient;
import com.winbee.vaasant.Utils.AssignmentData;
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

public class HomeworkDiscriptionFragment extends Fragment  {

    TextView titleHomeworkDescription,subjectHomeworkDescription,dateHomeworkDescription,attachmentTitleHomeworkDescription,doneOrNotHomeworkDescription;
    Button buttonAttachmentHomeworkDescription;
    RelativeLayout attachmentLayoutHomeworkDescription;
    private  static final int IMG_REQUEST=777;
    public static Bitmap bitmap;
    private ProgressBarUtil progressBarUtil;
    String UserId;

    private static final String TAG = "HomeworkDiscriptionFrag";
    String bucketId , AssisgnmentId;


    public HomeworkDiscriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homework_discription, container, false);

        titleHomeworkDescription=view.findViewById(R.id.titleHomeworkDescription);
        subjectHomeworkDescription=view.findViewById(R.id.subjectHomeworkDescription);
        dateHomeworkDescription=view.findViewById(R.id.dateHomeworkDescription);
        attachmentTitleHomeworkDescription=view.findViewById(R.id.attachmentTitleHomeworkDescription);
        doneOrNotHomeworkDescription=view.findViewById(R.id.doneOrNotHomeworkDescription);
        buttonAttachmentHomeworkDescription=view.findViewById(R.id.buttonAttachmentHomeworkDescription);
        attachmentLayoutHomeworkDescription=view.findViewById(R.id.attachmentLayoutHomeworkDescription);
        UserId= SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
        progressBarUtil = new ProgressBarUtil(getActivity());

        getInfo();
        addAttachment();




        return view;
    }

    private void addAttachment() {

        buttonAttachmentHomeworkDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialogForAttachment bottomDialogForAttachment = new bottomDialogForAttachment();
                bottomDialogForAttachment.show(getFragmentManager(),"bottomDialogForAttachment");

            }
        });

    }

    private void getInfo() {
        Bundle bundle=getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String subject = bundle.getString("subject");
            String date = bundle.getString("date");
            String pdfUrl = bundle.getString("pdfUrl");
            bucketId=bundle.getString("bucketId");
            AssisgnmentId=bundle.getString("AssignmentId");


            Log.d("TAG", "getInfo: "+bucketId+" "+AssisgnmentId);
            
            titleHomeworkDescription.setText(title);
            subjectHomeworkDescription.setText(subject);
            dateHomeworkDescription.setText(date);
            attachmentTitleHomeworkDescription.setText(title);


            openThePdf(pdfUrl);

        }else{
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }




    }

    private void openThePdf(final String pdfUrl) {
        attachmentLayoutHomeworkDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo open pdf on web herTo
                Toast.makeText(getContext()," opening pdf", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(pdfUrl); // missing 'http://' will cause crashed
                /// Add activity to open pdf  url
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }


    public void onPhotoAttachmentClicked() {
        //todo open intent for image picking
       // Toast.makeText(getContext()," on photo clicked", Toast.LENGTH_SHORT).show();
        selectImage();
    }

    public void onPdfAttachmentClicked() {
        //todo open image for pdf picking
        Toast.makeText(getContext()," on pdf clicked", Toast.LENGTH_SHORT).show();
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

               //image_view.setImageBitmap(bitmap);

                Toast.makeText(getActivity(), "File Selected", Toast.LENGTH_SHORT).show();
//                addImageButton.setVisibility(View.GONE);
//                uploadButton.setVisibility(View.VISIBLE);



                DialogAttachment dialogAttachment = new DialogAttachment();
                if (getFragmentManager() != null) {
                    dialogAttachment.show(getFragmentManager(),"DialogAttachment");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String imageToString()
    {

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//
//        return Base64.encodeToString(byteArray, Base64.DEFAULT);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void onImageClicked(Bitmap bitmap){
        //todo upload that bitmap here

        doneOrNotHomeworkDescription.setText("Submited");
      String str1=  imageToString();
        Log.d("Tag", "onImageClicked: "+ str1);
        callSubmitAssignment(str1);
    }
    private void callSubmitAssignment(String str1){
        Log.d(TAG, "callSubmitAssignment: ");
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<SubmitAssignment> call = mService.getSubmitAssignment("WB_007",UserId,bucketId,AssisgnmentId,imageToString(),"null","Photo");
        Log.d(TAG, "callSubmitAssignment: "+UserId+" "+bucketId+" "+AssisgnmentId+"-"+imageToString());
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
}
// to wo v nhi ja raha jo pahle jata tha
//apn ne to kuch change nahi kiya lekin