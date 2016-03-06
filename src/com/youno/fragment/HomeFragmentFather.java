package com.youno.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.youno.R;
import com.youno.activity.CityActivity;
import com.youno.view.TitleBarView;
import com.youno.widget.BaiduMapControl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class HomeFragmentFather extends Fragment {
	private static final String TAG = "HomeFragment";
	private Context mContext;
	private View mBaseView;
	private static TitleBarView mTitleBarView;
	private View mPopView;
	private PopupWindow mPopupWindow;
	private RelativeLayout mCanversLayout;
	private BaiduMapControl mBaiduMapControl;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mBaseView = inflater.inflate(R.layout.fragment_home_father, container, false);
		mPopView = inflater.inflate(R.layout.common_search_l, null);
		mContext = getActivity();
		findView();
		initTitleView();
		init();
		return mBaseView;
	}
	
	private void findView() {
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mCanversLayout=(RelativeLayout) mBaseView.findViewById(R.id.rl_canvers);
		mBaiduMapControl = new BaiduMapControl();
	}
	
    private void initTitleView() {
    	mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.icon_head_location, R.string.default_city);
		mTitleBarView.setBtnRight(R.drawable.icon_head_home_scan);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent  intent=new Intent(mContext, CityActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				
			}
		});
		
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Toast.makeText(mContext, "scanActivity", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mTitleBarView.setSearchBoxVisible(View.VISIBLE);
				mCanversLayout.setVisibility(View.GONE);
			}
		});
		
        mTitleBarView.setSearchBoxOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
				mCanversLayout.setVisibility(View.VISIBLE);
				mTitleBarView.setSearchBoxVisible(View.GONE);
			}
		});
	}
    
    private void init() {
    	FragmentTransaction ft=getFragmentManager().beginTransaction();
		HomeFragment homeFragment = new HomeFragment();
		ft.replace(R.id.child_fragment, homeFragment, HomeFragmentFather.TAG);
		ft.commit();
		getCityLoaction();
		Bundle bundle = getArguments();
		//if(bundle.getString("citylocation")!=null){
		//    setCityLoaction(bundle.getString("citylocation"));
		//}
	}
    
    private void getCityLoaction(){
    	mBaiduMapControl.Location(mContext,
			new BDLocationListener() {
				@Override
				public void onReceiveLocation(BDLocation location) {
					setCityLoaction(location.getCity());  // 获取城市
					Toast.makeText(mContext, "当前定位："+location.getCity(),Toast.LENGTH_SHORT).show();
				}
    	});
    }
    
    public void setCityLoaction(String cityName){
    	mTitleBarView.setBtnLeft(R.drawable.icon_head_location, cityName);
    }
}
