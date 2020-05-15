package com.sstechcanada.resumeapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.fragments.jobs.GoogleJobs;

public class ResumeBackend extends AppCompatActivity {

    private WebView backend;
    private ProgressBar progressBar;
    private String url ="https://resume-builder-app-994e9.web.app/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_backend);
        backend = findViewById(R.id.webViewBackend);
        progressBar = findViewById(R.id.pbWebview);

        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
       // backend.setWebChromeClient(new WebChromeClient());
        backend.setWebViewClient(new WebViewClient());

        WebSettings webSettings = backend.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        backend.clearHistory();
        backend.clearCache(true);
        backend.getSettings().setUserAgentString(newUA);
        backend.loadUrl(url);


        backend.canGoBack();
        backend.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == MotionEvent.ACTION_UP
                    && backend.canGoBack()) {
                backend.goBack();
                return true;
            }
            return false;
        });



    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            backend.setVisibility(View.VISIBLE);
        }
    }

}
