package com.youno.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.youno.R;
import com.youno.model.shopItemInfo;
import com.youno.view.TitleBarView;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

public class ShopInfoActivity extends Activity {
	protected static final String TAG = "CityActivity";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ImageView mShopinfoPic;
	private ImageView mCallShop;
	private Button mBuyNow;
	private AsyncBitmapLoader asyncLoader = null;
	private String mShopPicUrl= "";
	private Bundle mBundle;
	private shopItemInfo extraInfo;
	//商店信息
	private TextView mShopActivityName;
	private TextView mShopAddress;
	private TextView mActivityPrice;
	private TextView moraginalPrice;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
		mContext = getApplicationContext();
		//获取categoryaFragement传递过来的值
		getExtraInfo();
		mShopPicUrl = this.extraInfo.getShopLogoUrl();
		this.asyncLoader = new AsyncBitmapLoader();
		findView();
		initTitleView();
		init();
	}
	
	private void findView() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mShopinfoPic = (ImageView) findViewById(R.id.iv_shop_picinfo);
		mCallShop = (ImageView) findViewById(R.id.iv_shopinfo_call);
		mBuyNow = (Button) findViewById(R.id.ll_buy_now);
	}

	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE,
				View.GONE, View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.icon_back, R.string.activity_shopinfo);
		mTitleBarView.setBtnRight(R.drawable.icon_share);
	}

	private void init() {
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		Bitmap bitmap = asyncLoader.loadBitmap(mShopinfoPic,   
				mShopPicUrl,  
	            new ImageCallBack() {  
	                @Override  
	                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
	                    imageView.setImageBitmap(bitmap);  
	                }  
	            });  
	          
	    if(bitmap == null)  
	    {  
	    	mShopinfoPic.setImageResource(R.drawable.ic_launcher);  
	    }  
	    else  
	    {  
	    	mShopinfoPic.setImageBitmap(bitmap);  
	    }
	    
	    mCallShop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "110"));
                ShopInfoActivity.this.startActivity(intent);
			}
		});
	    
	    mBuyNow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, VoucherPurchaseActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void getExtraInfo(){
		mBundle = this.getIntent().getExtras();
		this.extraInfo = (shopItemInfo) mBundle.getSerializable("shopiteminfo"); 
	}
}
