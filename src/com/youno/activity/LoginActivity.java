package com.youno.activity;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.youno.R;
import com.youno.db.UserDao;
import com.youno.model.User;
import com.youno.view.TextURLView;
import com.yunno.utils.HttpUtil;
import com.yunno.utils.JsonUtil;
import com.yunno.utils.PreferenceUtil;
import com.yunno.utils.ThreadPoolManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "LoginActivity";
	private Context mContext;
	private RelativeLayout rl_user;
	private EditText usernameEdit;
	private EditText passwordEdit;
	private Button mLogin;
	private Button register;
	private TextURLView mTextViewURL;
	private String phone;
	private String password;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e(TAG, result);
			switch (msg.what) {
			case 0:
				try {
					JSONObject json = new JSONObject(result);
					if (JsonUtil.getInt(json, JsonUtil.CODE) != 1) {
						showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
					} else {
						JSONObject userobj=json.getJSONObject("user");
						User user= JsonUtil.parseUser(userobj);
						new UserDao(LoginActivity.this).insert(user);
						showLongToast("登录成功");	
						PreferenceUtil.getInstance(LoginActivity.this).setUid(user.getId());
						PreferenceUtil.getInstance(LoginActivity.this).getString(PreferenceUtil.PHONE, user.getPhone());
					    finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				showLongToast("网络请求失败,请检查网络是否连接");
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext=this;
		findView();
		initTvUrl();
		init();
	}
	
	private void findView(){
		rl_user=(RelativeLayout) findViewById(R.id.rl_user);
		usernameEdit = (EditText) findViewById(R.id.account);
		passwordEdit = (EditText) findViewById(R.id.password);
		mLogin=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		mTextViewURL=(TextURLView) findViewById(R.id.tv_forget_password);
	}

	private void init(){
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		mLogin.setOnClickListener(this);
		register.setOnClickListener(this);
		if (new UserDao(this).queryAll().size()>0) {
			User mUser=new UserDao(this).queryAll().get(0);
			usernameEdit.setText(mUser.getPhone());
		}else {
			usernameEdit.setText(PreferenceUtil.getInstance(this).getString(PreferenceUtil.PHONE, ""));
		}
	}
	
	private void initTvUrl(){
		mTextViewURL.setText(R.string.forget_password);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login:
			if (checkdata()) {
				ThreadPoolManager.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						String result = HttpUtil.post(HttpUtil.URL_LOGIN,
								new BasicNameValuePair(JsonUtil.PHONE, phone),
								new BasicNameValuePair(JsonUtil.PASSWORD,
										password));
						Log.e(TAG, result);
						Message msg = new Message();
						msg.obj = result;
						msg.what=0;
						if(result.equals("网络请求失败,请检查网络是否连接")) msg.what=1;
						mHandler.sendMessage(msg);
					}
				});
				showLoadingDialog("正在登陆");				
			}
			break;
        case R.id.register:
        	showLoadingDialog("正在登陆");
			break;
		default:
			break;
		}
	}
	
	private boolean checkdata() {
		boolean isright = false;
		phone = usernameEdit.getText().toString().trim();
		password = passwordEdit.getText().toString().trim();
		if (phone == null || phone.equals("")) {
			showLongToast("电话号码不能为空");
		} else if (password == null || password.equals("")) {
			showLongToast("密码不能为空");
		} else {
			isright = true;
		}
		Log.i(TAG, isright + "");
		return isright;
	}
}
