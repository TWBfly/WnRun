package win.tang.wnrun.weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import win.tang.wnrun.MyApplication;
import win.tang.wnrun.ui.ParseActivity;
import win.tang.wnrun.util.NetworkUtil;
import win.tang.wnrun.util.StartActivityUtils;

import static android.view.KeyEvent.KEYCODE_BACK;
import static win.tang.wnrun.util.Constains.WN_URL;

/**
 * Created by Tang on 2018/8/27
 * 共同WebView
 */
public class CommonWebView {

    private Button parsing;
    private Activity activity;
    private WebView webView;
    private String url;

    public CommonWebView(Activity activity, Button parsing) {
        this.activity = activity;
        this.parsing = parsing;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(FrameLayout frameLayout, String setUrl) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(MyApplication.context);
        webView.setLayoutParams(params);
        frameLayout.addView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        //        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        if (!NetworkUtil.isConnectingToInternet(MyApplication.context)) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        //        webView.loadUrl("file:///android_asset/Test.html");


        webView.loadUrl(setUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("twb", "shouldOverrideUrlLoading url:" + url);
                setUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        url = request.getUrl().toString();

                        Log.e("twb", "url:" + url);
                        setUrl(url);
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String intercept_url = request.getUrl().toString();
                    String interceptUurl = intercept_url.toLowerCase();
                    if (interceptUurl.contains("st.gauro.cn")) {
                        return new WebResourceResponse(null, null, null);//去掉广告
                    } else {
                        return super.shouldInterceptRequest(view, request);
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {

            }

            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    private void setUrl(final String loading_url) {
        webView.loadUrl(loading_url);
        if (loading_url.contains("index.html")) {
            parsing.setVisibility(View.GONE);
        } else if (loading_url.contains("https://m.v.qq.com/")) {
            parsing.setVisibility(View.VISIBLE);
            parsing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonWebView.this.url.contains("https://m.v.qq.com/cover/")) {
                        String wnUrl = "https://wanneng.run/" + loading_url;
                        Bundle bundle = new Bundle();
                        bundle.putString(WN_URL, wnUrl);
                        StartActivityUtils.start(activity, ParseActivity.class, bundle);
                        Log.e("twb", "wnUrl:" + wnUrl);
                    } else {
                        Toast.makeText(activity, "请点击具体视频", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void DestoryWebView() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    public boolean keyDown(int keyCode) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            if (url.contains("index.html")) {
                return false;
            } else {
                webView.goBack();
                return true;
            }
        }
        return false;
    }
}

