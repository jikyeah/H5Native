package com.hkzy.imlz_h5native;

import android.os.Bundle;
import android.view.View;
import com.hkzy.imlz_h5native.util.ActivityUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.btnshare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKManager.showShare(MainActivity.this, "title", "baidu.com", "http://img.taopic.com/uploads/allimg/130711/318756-130G1222R317.jpg", "content", "comment");
            }
        });


        ActivityUtil.next(this, MainWebViewActivity.class);

    }

}
