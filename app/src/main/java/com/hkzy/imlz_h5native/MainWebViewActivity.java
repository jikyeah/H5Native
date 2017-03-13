package com.hkzy.imlz_h5native;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hkzy.imlz_h5native.widget.HTML5CustomWebView;
import com.umeng.analytics.MobclickAgent;

/**
 * 网页首页
 */

public class MainWebViewActivity extends BaseActivity {
    private HTML5CustomWebView mWebView;
    private String ad_url = !TextUtils.isEmpty(AppConfig.Web_Url) ? AppConfig.Web_Url : "file:///android_asset/empty.html";
    private String title = AppConfig.App_Name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new HTML5CustomWebView(this, MainWebViewActivity.this, title, ad_url);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //准备javascript注入
        mWebView.addJavascriptInterface(
                new Js2JavaInterface(), "HN");
        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            if (ad_url != null) {
                mWebView.startLoadUrl(ad_url);
            }
        }

        mWebView.wv_imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        setContentView(mWebView.getLayout());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mWebView != null) {
            mWebView.saveState(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
        MobclickAgent.onResume(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWebView != null) {
            mWebView.stopLoading();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.doDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void closeAdWebPage() {

    }

    @Override
    public void onBackPressed() {
        if (ShareSDKManager.isMshareing()) {
            ShareSDKManager.setCancelShare(true);
        } else {
            goBack();
        }
    }

    private void goBack() {
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                //mWebView.releaseCustomview();

                confirmQuit();
            }
        }
    }


    /**
     * 确认是否退出弹出框
     */
    @SuppressLint("NewApi")
    private void confirmQuit() {

        MaterialDialog dialog = new MaterialDialog.Builder(MainWebViewActivity.this)
                .content(R.string.confirm_quit)
                .contentColor(ContextCompat.getColor(this, R.color.black_000000))
                .backgroundColorRes(R.color.white_FFFFFF)
                .negativeText("确定")
                .negativeColor(getResources().getColor(R.color.black_000000, null))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        try {
                            mWebView.releaseCustomview();
                            quitApp();
                        } catch (Exception e) {

                        }

                    }
                })
                .positiveText("取消")
                .positiveColor(getResources().getColor(R.color.black_000000, null))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                })
                .build();

        dialog.show();

    }

}