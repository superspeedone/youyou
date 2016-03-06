package com.youno.fragment;

import com.youno.R;
import com.youno.view.TitleBarView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class OrderFragment extends Fragment implements OnClickListener {
	private static final String TAG = "OrderFragment";
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private RelativeLayout mScanorderdRelative;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mBaseView = inflater.inflate(R.layout.fragment_order, container, false);
		mContext = getActivity();
		findView();
		initTitleView();
		init();
		return mBaseView;
	}

	
	private void findView() {
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mScanorderdRelative = (RelativeLayout) mBaseView.findViewById(R.id.layout_scan_order);
	}
	
	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE, View.GONE,
				View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.bottom_tab_order_check, R.string.menu_order);
		mTitleBarView.setBtnRight(R.drawable.checkout_record);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Toast.makeText(mContext, "allOrderActivity", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	private void init() {
		mScanorderdRelative.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.layout_scan_order:
			Toast.makeText(this.getActivity().getApplicationContext(), "扫码买单", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
