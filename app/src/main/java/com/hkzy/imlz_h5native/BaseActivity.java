package com.hkzy.imlz_h5native;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hkzy.imlz_h5native.util.ActivityManageUtil;
import com.hkzy.imlz_h5native.util.ActivityUtil;
import com.hkzy.imlz_h5native.util.PackageUtils;

/**
 * Activity基础 所有的页面都需继承此Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Dialog mDialog;
    private Toast generalToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActivityManageUtil.getActivityManager().pushActivity(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        /*((ImppApplication) getApplication()).setCurrentActivity(this);
        CrashUtils.onResume(this);*/
    }

    /**
     * 获取当前view的LayoutInflater实例
     *
     * @return
     */
    protected LayoutInflater getLayouInflater() {
        LayoutInflater _LayoutInflater = LayoutInflater.from(this);
        return _LayoutInflater;
    }

    /**
     * 公共显示消息
     *
     * @param msg
     */
    protected void showToastMsg(String msg) {
        if (generalToast == null) {
            generalToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
            generalToast.setGravity(Gravity.CENTER, 0, 0);
        }
        if (!TextUtils.isEmpty(msg) && !PackageUtils.isBackgroundRunning(this, BaseActivity.this.getPackageName())) {
            generalToast.setText(msg);
            generalToast.show();
        }
    }

    /**
     * 获取(创建)Loading弹出框
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    private Dialog getLoadingDialog(final Context context) {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(context)
                    .content(R.string.please_wait)
                    .contentColor(ContextCompat.getColor(this, R.color.black_000000))
                    .progress(true, 0)
                    .cancelable(false)
                    .backgroundColorRes(R.color.white_FFFFFF)
                    .build();

            mDialog.setOnCancelListener(null);
            mDialog.setCanceledOnTouchOutside(false);
        }

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Dialog alertDialog = (Dialog) dialog;
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

        return mDialog;
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        if (!isFinishing()) {
            Dialog dialog = getLoadingDialog(BaseActivity.this);
            if (!dialog.isShowing()) {
                dialog.show();
            } else {
                return;
            }
        }
    }

    /**
     * 显示输入框并设置OnKeyListener
     *
     * @param listener
     */
    public void showLoadingDialog(DialogInterface.OnKeyListener listener) {
        if (!isFinishing()) {
            Dialog dialog = getLoadingDialog(BaseActivity.this);
            if (!dialog.isShowing()) {
                if (listener != null) {
                    dialog.setOnKeyListener(listener);
                }
                dialog.show();
            } else {
                return;
            }
        }
    }

    /**
     * 显示加载框
     *
     * @param context
     */
/*    public void showLoadingDialog(Context context) {
        if (!isFinishing()) {
            getLoadingDialog(context).show();
        }
    }*/


    /**
     * 停止显示加载框
     */
    public void stopLoadingDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 停止显示加载框,且停止网络请求
     */
    public void stopLoadingDialogWithRequest(Context context) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        //TODO
        //HttpManagerApi.cancelHttpRequest(context);
    }


    @Override
    public void onUserInteraction() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        ActivityUtil.goBack(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /*protected void initTitleBar(String title) {
        ((TextView) findViewById(R.id.title)).setText(title);
        findViewById(R.id.headerLeftBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLoadingDialog();
        ActivityManageUtil.getActivityManager().popActivity(this);
        System.gc();
    }

    protected void quitApp() {
        try {
            ActivityManageUtil.getActivityManager().popAllActivity();
            System.exit(0);
            Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
