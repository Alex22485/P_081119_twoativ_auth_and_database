package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Website extends AppCompatActivity {

    String url="https://www.google.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        WebView siteview=(WebView)findViewById(R.id.webView);

//        siteview.getSettings().setJavaScriptEnabled(true);
//        siteview.getSettings().setLoadWithOverviewMode(true);
//        siteview.getSettings().setUseWideViewPort(true);
//
//        siteview.setWebViewClient(new MywebViewClient());
//
//        siteview.loadUrl(url);


        MywebViewClient dsd=new MywebViewClient();
        boolean ss;

        ss=dsd.shouldOverrideUrlLoading(siteview,url);
        Toast.makeText( Website.this, "Соединение"+ss, Toast.LENGTH_SHORT ).show();




    }



    private class MywebViewClient extends WebViewClient {
        @Override
        public  boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }
}
