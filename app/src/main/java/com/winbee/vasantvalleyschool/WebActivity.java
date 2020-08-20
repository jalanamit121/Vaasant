package com.winbee.vasantvalleyschool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.winbee.vasantvalleyschool.Models.UrlName;
import com.winbee.vasantvalleyschool.Utils.AssignmentData;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private UrlName urlName;
    private ProgressBarUtil progressBarUtil;
    Button btm_asked_question;
    String Url;
    String googleDocs = "https://docs.google.com/viewer?url=";

   // @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView =findViewById(R.id.myWebView);
        btm_asked_question=findViewById(R.id.btm_asked_question);


        progressBarUtil   =  new ProgressBarUtil(this);
        progressBarUtil.showProgress();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            urlName = (UrlName) bundle.getSerializable("PDFURL");
            if(urlName!=null){
                System.out.println("Suree:"+urlName.getURL().equalsIgnoreCase("PDFURL"));
              //  Url=urlName.getURL();
        }
        }
        displayWebView();
        btm_asked_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WebActivity.this,AskedQuestionActivity.class);
                intent.putExtra("documentID",urlName.getDocumentId());
                startActivity(intent);
            }
        });
    }

    private void displayWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(AssignmentData.PdfUrl);
                return true;

            }
        });
        webView.loadUrl(AssignmentData.PdfUrl);

    }

}
