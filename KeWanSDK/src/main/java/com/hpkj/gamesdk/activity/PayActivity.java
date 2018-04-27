package com.hpkj.gamesdk.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.interf.PayUrlCallBack;
import com.hpkj.gamesdk.network.GameNetWork;
import com.hpkj.gamesdk.utils.GameUtils;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.utils.SharePreferenceUtils;
import com.hpkj.gamesdk.utils.XActivityUtils;

/**
 * @author huanglei
 * @ClassNname：PayActivity.java
 * @Describe 支付页面
 * @time 2018/3/23 16:20
 */

public class PayActivity extends Activity {
    private WebView mWebView;
    String url;
    String gid;
    String userid;
    String ext;
    String coins;
    int kewan_activity_pay;
    int kewan_webview_pay;
    int type;
    boolean isPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kewan_activity_pay = ResourceUtil.getLayoutId(PayActivity.this, "kewan_activity_pay");
        setContentView(kewan_activity_pay);
        kewan_webview_pay = ResourceUtil.getId(PayActivity.this, "kewan_webview_pay");
        mWebView = findViewById(kewan_webview_pay);
        url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", 0);
        WebSettings settings = mWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.setVerticalScrollbarOverlay(true);
        gid = getIntent().getStringExtra("gid");
        userid = getIntent().getStringExtra("userid");
        ext = getIntent().getStringExtra("ext");
        coins = getIntent().getStringExtra("coins");
        Log.w("cc","url>>>"+url);
        if (type == 1) {//支付宝支付
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.loadUrl(url);
        } else if (type == 2) {//微信支付
            mWebView.setVisibility(View.VISIBLE);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                    if (url.startsWith("weixin://wap/pay?")) {
                        try {
                            SharePreferenceUtils.putBoolean(PayActivity.this, "isPay", true);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } catch (ActivityNotFoundException e) {
                            SharePreferenceUtils.putBoolean(PayActivity.this, "isPay", false);
                            XActivityUtils.getInstance().popActivity(PayActivity.this);
                            Toast.makeText(PayActivity.this, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
            });
            mWebView.loadUrl(url);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        isPay = SharePreferenceUtils.getBoolean(PayActivity.this, "isPay", false);
        if (isPay == true) {
            SharePreferenceUtils.putBoolean(PayActivity.this, "isPay", false);
            Intent intent = new Intent(this, PayResultActivity.class);
            intent.putExtra("ext", ext);
            intent.putExtra("url", url);
            startActivity(intent);
            XActivityUtils.getInstance().popActivity(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            if (!(url.startsWith("http") || url.startsWith("https"))) {
                return true;
            }
            /**
             * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
             */
            PayTask task = new PayTask(PayActivity.this);
            boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                @Override
                public void onPayResult(final H5PayResultModel result) {
                    handler.sendMessage(handler.obtainMessage(1, result));
                }
            });

            /**
             * 判断是否成功拦截
             * 若成功拦截，则无需继续加载该URL；否则继续加载
             */
            if (!isIntercepted)
                view.loadUrl(url);
            return false;
        }
    }


    public void uploadPayInfo(String returnurl) {
        GameNetWork.uploadPayInfo(new MyBaseProgressCallbackImpl<String>() {
            @Override
            public void onSuccess(String resul) {
                super.onSuccess(resul);
            }
        }, returnurl);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                final H5PayResultModel result = (H5PayResultModel) msg.obj;
                if (result.getReturnUrl() != null) {
                    uploadPayInfo(result.getReturnUrl());
                }
                PayUrlCallBack payUrlCallBack = new PayUrlCallBack(GameUtils.mPayUrlInterf);
                payUrlCallBack.payState(result);
                XActivityUtils.getInstance().popActivity(PayActivity.this);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            try {
                mWebView.destroy();
            } catch (Throwable t) {
            }
            mWebView = null;
        }
    }
}
