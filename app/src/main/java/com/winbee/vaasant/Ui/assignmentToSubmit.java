package com.winbee.vaasant.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.winbee.vaasant.R;


public class assignmentToSubmit extends Fragment {

    private WebView webView;
    Button btm_asked_question;
    private String bucketId;
    private String AssisgnmentId;


    public assignmentToSubmit() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment_to_submit, container, false);
        webView=view.findViewById(R.id.myWebView);
        btm_asked_question=view.findViewById(R.id.submitAssignmentBtn);


        getInfo();

        return  view;
    }
    private void displayWebView(final String urlName) {
        Log.d("TAG", "displayWebView: "+urlName);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(urlName);
                return true;

            }
        });
        webView.loadUrl(urlName);

    }
    private void getInfo() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            final String title = bundle.getString("title");
            final String subject = bundle.getString("subject");
            final String date = bundle.getString("date");
            final String pdfUrl = bundle.getString("pdfUrl");
            bucketId = bundle.getString("bucketId");
            AssisgnmentId = bundle.getString("assignmentId");

            btm_asked_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SubmitAssignmentHomework submitAssignment = new SubmitAssignmentHomework();
                    FragmentTransaction fragmentTransaction = null;
                    if (getFragmentManager() != null) {
                        fragmentTransaction = getFragmentManager().beginTransaction()
                                .replace(R.id.homeFrame, submitAssignment, "SubmitAssignmentHomework");
                        Bundle bundleHomework = new Bundle();
                        bundleHomework.putString("title", title);
                        bundleHomework.putString("subject", subject);
                        bundleHomework.putString("date", date);
                        bundleHomework.putString("pdfUrl", pdfUrl);
                        bundleHomework.putString("bucketId", bucketId);
                        bundleHomework.putString("assignmentId", AssisgnmentId);
                        submitAssignment.setArguments(bundleHomework);

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }


                }
            });


            displayWebView(pdfUrl);

        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}