package com.hkzy.imlz_h5native.util;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;

import java.util.List;

public class PackageUtils {

    public static final String TAG = "PackageUtils";


    /**
     * application is running background
     * @param context
     * @return
     */
    public static boolean isBackgroundRunning(Context context , String processName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (activityManager == null) return false;
// get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState) return true;
                else return false;
            }
        }
        return false;
    }

}
