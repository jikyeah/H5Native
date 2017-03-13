
package com.hkzy.imlz_h5native;

import android.os.Bundle;
import android.os.Handler;

import com.hkzy.imlz_h5native.util.ActivityUtil;
import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        ActivityUtil.next(SplashActivity.this, MainWebViewActivity.class, R.anim.alpha_anim_in, R.anim.alpha_anim_out);
                        SplashActivity.this.finish();
            }
        }, 1000);


        /*HttpClientManager.requestWebUrl(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (msg.obj != null && !TextUtils.isEmpty(msg.obj.toString())) {

                            String url = msg.obj.toString();
                            AppConfig.Web_Url = url;

                            //开启新页面
                        }
                        Log.d("IAMLZ", "网页URL:" + AppConfig.Web_Url);
                        break;
                    case -1:
                        //请求失败程序保持继续
                        //开启新页面
                        Log.d("IAMLZ", "网页URL请求失败");
                        break;
                }
            }
        });*/


    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
