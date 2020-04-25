package com.sstechcanada.resumeapp.fragments.jobs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sstechcanada.resumeapp.R;


public class Indeed extends Fragment {

    private WebView jobSearch;
    private String base_url = "https://www.indeed.co.in/jobs?q=";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.job_search_webview, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        final String jobquery = bundle.getString("keyword");

        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        jobSearch = rootView.findViewById(R.id.webview);
        jobSearch.getSettings().setJavaScriptEnabled(true);
        jobSearch.setWebChromeClient(new WebChromeClient());
        jobSearch.getSettings().setPluginState(WebSettings.PluginState.ON);
        jobSearch.clearHistory();
        jobSearch.clearCache(true);
        //jobSearch.getSettings().setUserAgentString(newUA);
        jobSearch.loadUrl(base_url + jobquery + "&l=");


        jobSearch.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });


        jobSearch.canGoBack();
        jobSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == MotionEvent.ACTION_UP
                    && jobSearch.canGoBack()) {
                jobSearch.goBack();
                return true;
            }
            return false;
        });


        return rootView;
    }
}
