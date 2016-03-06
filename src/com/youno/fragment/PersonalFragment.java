package com.youno.fragment;

import com.youno.activity.CustomSettingActivity;
import com.youno.activity.LoginActivity;
import com.youno.activity.OrderActivity;
import com.youno.activity.ShopInfoActivity;
import com.youno.db.UserDao;
import com.youno.model.User;
import com.youno.view.TitleBarView;
import com.youno.R;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.HttpUtil;
import com.yunno.utils.ImageLoaderUtil;
import com.yunno.utils.ImageUtil;
import com.yunno.utils.PreferenceUtil;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalFragment extends Fragment implements OnClickListener {
	private static final String TAG = "PersonalFragment";
	private AsyncBitmapLoader asyncLoader = null;
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private RelativeLayout mVoucherRelative;
	private RelativeLayout mCertificateRelative;
	private RelativeLayout mRedpaperRelative;
	private RelativeLayout mCustomerServiceRelative;
	private RelativeLayout mOrderRelative;
	private ImageView mUserFace;
	private TextView mLogin;
	private User mUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mBaseView = inflater.inflate(R.layout.fragment_personal, container, false);
		asyncLoader = new AsyncBitmapLoader();
		mContext = getActivity();
		findView();
		initTitleView();
		init();
		return mBaseView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mUser=new UserDao(mContext).queryById(PreferenceUtil.getInstance(mContext).getUid());
		if (mUser!=null) {
			mLogin.setText(mUser.getName());
			if (mUser.getImage().equals("")) {
				mUserFace.setImageResource(R.drawable.my_face);
			}else {
				//ImageLoaderUtil.displayImage(HttpUtil.SERVER+mUser.getImage(), mUserFace, mContext);
				Bitmap bitmap = asyncLoader.loadBitmap(mUserFace,   
						HttpUtil.SERVER+mUser.getImage(),  
			            new ImageCallBack() {  
			                @Override  
			                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
			                    imageView.setImageBitmap(ImageUtil.toRoundBitmap(bitmap,Color.WHITE,25));  
			                }  
			    });  
			          
			    if(bitmap == null) {  
			    	mUserFace.setImageResource(R.drawable.my_face);  
			    }  
			    else {  
			    	mUserFace.setImageBitmap(ImageUtil.toRoundBitmap(bitmap,Color.WHITE,25));  
			    }
			    ImageUtil.bitmapRecyle(bitmap);
			}
		}else {
			mLogin.setText("登陆");
			mUserFace.setImageResource(R.drawable.my_face);
		}
	}

	private void findView() {
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mVoucherRelative = (RelativeLayout) mBaseView.findViewById(R.id.id_voucher);
		mCertificateRelative = (RelativeLayout) mBaseView.findViewById(R.id.id_certificate);
		mRedpaperRelative = (RelativeLayout) mBaseView.findViewById(R.id.id_redpaper);
		mCustomerServiceRelative = (RelativeLayout) mBaseView.findViewById(R.id.id_customrService);
		mOrderRelative = (RelativeLayout) mBaseView.findViewById(R.id.tv_all_order);
		mUserFace = (ImageView) mBaseView.findViewById(R.id.iv_user_face);
		mLogin = (TextView) mBaseView.findViewById(R.id.tv_login);
	}



	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE, View.GONE,
				View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.bottom_tab_personal_check, R.string.menu_personal);
		mTitleBarView.setBtnRight(R.drawable.icon_head_set);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, CustomSettingActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void init() {
		mVoucherRelative.setOnClickListener(this);
		mCertificateRelative.setOnClickListener(this);
		mRedpaperRelative.setOnClickListener(this);
		mCustomerServiceRelative.setOnClickListener(this);
		mOrderRelative.setOnClickListener(this);
		mLogin.setOnClickListener(this);
		//new MySyncTack().execute(mContext);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.id_voucher:
            
			break;
		case R.id.id_certificate:
			break;
		case R.id.id_redpaper:
			break;
		case R.id.id_customrService:
			break;
		case R.id.tv_all_order:
			intent.setClass(mContext, OrderActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_login:
			if (mUser==null) {
				intent.setClass(mContext, LoginActivity.class);
				startActivity(intent);
			}else{
				intent.setClass(mContext, LoginActivity.class);
				startActivity(intent);
				Toast.makeText(mContext, "personalCenter", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
	
}
