package com.youno.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youno.R;
import com.youno.R.string;
import com.youno.view.TitleBarView;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

public class CustomSettingActivity extends Activity {
	protected static final String TAG = "CityActivity";
	private Context mContext;
	private TitleBarView mTitleBarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_setting);
		mContext = getApplicationContext();
		findView();
		initTitleView();
		init();
	}
	
	private void findView() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
	}

	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
				View.GONE, View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.icon_back,R.string.empty_string);
		mTitleBarView.setTitleText(R.string.custom_setting);
	}

	private void init() {
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
}
