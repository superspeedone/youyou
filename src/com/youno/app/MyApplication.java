package com.youno.app;

import java.util.LinkedList;

import android.app.Activity;
import android.app.Application;
import com.baidu.mapapi.SDKInitializer;
import com.yunno.utils.PreferenceUtil;


public class MyApplication extends Application {

	public int time = 0;
	public int type = 1;
	public int orderindex = 0;
	public int islocation = 0;
	private LinkedList<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(this);
	
	}

	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		PreferenceUtil.getInstance(this).setString(PreferenceUtil.CITYID, "0");
		System.exit(0);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}
