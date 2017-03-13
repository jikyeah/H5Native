package com.hkzy.imlz_h5native.util;

import android.app.Activity;

import java.util.Stack;

/**
 * 
 *功能：activity 栈管理
 * 
 */
public class ActivityManageUtil {
	private static Stack<Activity> activityStack;
	private static ActivityManageUtil instance;

	private ActivityManageUtil() {
	}

	public static ActivityManageUtil getActivityManager() {
		if (instance == null) {
			instance = new ActivityManageUtil();
		}
		return instance;
	}

	public void popActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	/**
	 * activity 出栈
	 * 
	 * @param activity
	 */
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	public Activity currentActivity() {
		if (activityStack.size() > 0) {
			Activity activity = activityStack.lastElement();
			return activity;
		} else {
			return null;
		}
	}

	/**
	 * activity 进栈
	 * 
	 * @param activity
	 */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}

	public void popAllActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}

			popActivity(activity);
		}
	}
}
