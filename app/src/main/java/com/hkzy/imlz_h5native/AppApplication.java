package com.hkzy.imlz_h5native;

import android.app.Application;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by linzenos
 */
public class AppApplication extends Application {

    private static AppApplication application = null;


    public static AppApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;


        AppConfig.App_Name = getResources().getString(R.string.app_name);

        initUMeng();

    }

    /**
     * 初始化UMeng
     */
    private void initUMeng() {
        if (!TextUtils.isEmpty(AppConfig.UM_APP_KEY) && !TextUtils.isEmpty(AppConfig.UM_CHANNEL_ID)) {
            MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(getApplicationContext(), AppConfig.UM_APP_KEY, AppConfig.UM_CHANNEL_ID);
            MobclickAgent.startWithConfigure(config);
        }
    }


}
