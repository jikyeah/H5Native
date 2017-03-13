package com.hkzy.imlz_h5native;

import android.os.Handler;
import android.os.Message;
import com.hkzy.imlz_h5native.util.HttpUtils;


/**
 * 网络访问
 * Created by linzenos on 16/9/21.
 */
public class HttpClientManager {


    public static void requestWebUrl(final Handler handler) {

        Message message = new Message();
        message.what = 1;
        message.obj = "http://www.kcpnk.com/";
        handler.sendMessage(message);
        try {
            HttpUtils.doPostAsyn("", "", new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(String result) {

                    Message message = new Message();
                    message.what = 1;
                    message.obj = result;
                    handler.sendMessage(message);

                }
            });
        } catch (Exception e) {
            handler.sendEmptyMessage(-1);
        }

    }


}
