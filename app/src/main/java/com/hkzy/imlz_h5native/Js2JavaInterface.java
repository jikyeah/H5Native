package com.hkzy.imlz_h5native;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * 为JS提供的本地能力接口
 * Created by linzenos on 2017/1/16.
 */

public class Js2JavaInterface {
    private Context context;

    /**
     * 提示消息
     *
     * @param message
     */
    @JavascriptInterface
    public void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 启动分享
     *
     * @param title    标题
     * @param url      分享内容的url
     * @param imageUrl 图片url地址
     * @param content  分享的内容
     * @param comment  对分享的评论(可选)
     */
    @JavascriptInterface
    public void share(String title, String url, String imageUrl, String content, String comment) {
        ShareSDKManager.showShare(AppApplication.getInstance(), title, url, imageUrl, content, comment);
    }
}
