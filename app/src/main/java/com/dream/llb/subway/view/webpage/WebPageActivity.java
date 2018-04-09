package com.dream.llb.subway.view.webpage;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dream.llb.subway.R;

public class WebPageActivity extends AppCompatActivity {
    private WebView webView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);
        url = getIntent().getStringExtra("url");
        initWebView();
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("llb", "url=" + url);
//                if ( url.contains("ditiezu") == true){
                    view.loadUrl(url);
                    return true;
//                }else{
//                    Intent in = new Intent (Intent.ACTION_VIEW , Uri.parse(url));
//                    startActivity(in);
//                    return true;
//                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                titleview.setText(title);//a textview
            }
        });
    }
}
