package com.youno.activity;


import com.youno.MainActivity;
import com.youno.R;
import com.yunno.utils.PreferenceUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {
	protected static final String TAG = "WelcomeActivity";
	private Context mContext;
	private ImageView mImageView;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mContext = this;
		findView();
		init();
	}

	private void findView() {
		mImageView = (ImageView) findViewById(R.id.iv_welcome);
	}

	@SuppressWarnings("static-access")
	private void init() {
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
				Boolean isFirst = PreferenceUtil.getInstance(mContext).getBoolean("isFirst", true);
				if (isFirst) {
					PreferenceUtil.getInstance(mContext).setBoolean("isFirst", false);
					Intent intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		},2000);
		
	}
}
