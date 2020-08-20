package com.winbee.vasantvalleyschool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.winbee.vasantvalleyschool.Utils.AssignmentData;
import com.winbee.vasantvalleyschool.helper.MyWebChromeClient;
import com.winbee.vasantvalleyschool.helper.VideoEnabledWebView;


public class DriveVideoPlayerActivity extends AppCompatActivity {
    private VideoEnabledWebView andExoPlayerView;
    private String TEST_URL_HLS = "https://drive.google.com/file/d/1AOroyeM5P8ZwZ15EGqR4SvRDW7lHoqeZ/preview";
    TextView txt_subject,txt_topic,txt_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_videoplayer_layout);
        Log.i("ïnfo","Launched new activity");
        Intent intent = getIntent();
        String url = intent.getStringExtra("dURL");
        //String newURL=url.replace("view","preview");
       // String newURL=url.replace("view","preview");
        String array1[]= url.split("view");

        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        txt_subject = findViewById(R.id.txt_subject);
        txt_subject.setText("Subject- "+AssignmentData.Subject);
        txt_topic = findViewById(R.id.txt_topic);
        txt_topic.setText("Topic- "+AssignmentData.Topic);
        txt_teacher = findViewById(R.id.txt_teacher);
        txt_teacher.setText("Teacher- "+AssignmentData.Faculty);

        CookieManager.getInstance().setAcceptCookie(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(andExoPlayerView, true);
        }

        WebSettings webSettings = andExoPlayerView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        andExoPlayerView.setWebViewClient(new WebViewClient());
        andExoPlayerView.setWebChromeClient(new MyWebChromeClient());
        andExoPlayerView.setWebChromeClient(new MyChrome());
        andExoPlayerView.loadUrl(array1[0]+"preview");
        Log.i("ïnfo","Launched new activity"+array1[0]);
        //Toast.makeText(this, "URL-"+newURL, Toast.LENGTH_SHORT).show();
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
