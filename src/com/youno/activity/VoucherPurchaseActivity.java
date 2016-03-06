package com.youno.activity;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youno.R;
import com.youno.R.string;
import com.youno.view.TitleBarView;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

public class VoucherPurchaseActivity extends Activity {
	protected static final String TAG = "CityActivity";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private TextView mVoucherCounts;
	private TextView mPrice;
	private TextView mTotal;
	private TextView mPurchaseTotal;
	private Button mDishDes;
	private Button mDishInc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_voucher_activity);
		mContext = getApplicationContext();
		findView();
		initTitleView();
		init();
	}
	
	private void findView() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mVoucherCounts = (TextView) findViewById(R.id.et_voucher_purcharse_counts);
		mPrice = (TextView) findViewById(R.id.tv_voucher_price);
		mTotal = (TextView) findViewById(R.id.tv_voucher_purchase_total);
		mPurchaseTotal = (TextView) findViewById(R.id.tv_voucher_total);
		mDishDes = (Button) findViewById(R.id.btn_dish_item_dec);
		mDishInc = (Button) findViewById(R.id.btn_dish_item_inc);
	}

	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
				View.GONE, View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.icon_back,R.string.empty_string);
		mTitleBarView.setTitleText(R.string.voucher_purchase);
	}

	private void init() {
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		mDishDes.setEnabled(false);
		mVoucherCounts.addTextChangedListener(textWatcher);
		mDishDes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int counts = Integer.parseInt(mVoucherCounts.getText().toString().trim());
				if(counts>0) {
					counts -= 1;
					mDishInc.setEnabled(true);
				}else {
					v.setEnabled(false);
				}
				mVoucherCounts.setText(String.valueOf(counts));
			}
		});
		
       mDishInc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int counts = Integer.parseInt(mVoucherCounts.getText().toString().trim());
				counts += 1;
				mVoucherCounts.setText(String.valueOf(counts));
				mDishDes.setEnabled(true);
				if(counts == 20 ) v.setEnabled(false);
			}
		});
	}
	
	private TextWatcher textWatcher = new TextWatcher(){
		

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			int price = Integer.parseInt(mPrice.getText().toString().trim());
			int counts = Integer.parseInt(mVoucherCounts.getText().toString());
			String total;
			total = String.valueOf(price*counts);
			mTotal.setText(total);
			mPurchaseTotal.setText(total);
			if(price*counts == 0){
				mDishDes.setEnabled(false);
			}
		}
		
	};
	
}
