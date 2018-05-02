package com.dream.llb.subway.view.webpage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;

public class WebPageActivity extends BaseActivity implements View.OnClickListener {
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
        initHeadView();
        headTitleTV.setText("地铁族");
        headRightTV.setVisibility(View.GONE);
        headLeftIcon.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i("llb", "url=" + url);
//                if ( url.contains("ditiezu") == true){
                view.loadUrl(url);
                return true;
//                }else{
//                    Intent in = new Intent (Intent.ACTION_VIEW , Uri.parse(url));
//                    startActivity(in);
//                    to_left true;
//                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                titleview.setText(title);//a textview
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headLeftIcon:
                finish();
                break;
        }
    }
}
