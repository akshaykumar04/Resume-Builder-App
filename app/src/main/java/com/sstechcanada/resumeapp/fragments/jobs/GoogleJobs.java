package com.sstechcanada.resumeapp.fragments.jobs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sstechcanada.resumeapp.R;


public class GoogleJobs extends Fragment {

    private WebView jobSearch;
    private String base_url = "https://careers.google.com/jobs/results/?company=Google&company=Google%20Fiber&company=YouTube&employment_type=FULL_TIME&hl=en_US&jlo=en_US&q=";


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
        jobSearch.loadUrl(base_url + jobquery);


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
