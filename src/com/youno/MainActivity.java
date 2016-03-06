package com.youno;

import com.yunno.utils.Constant;
import com.yunno.utils.DialogUtil;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.youno.R;
import com.youno.activity.CityActivity;
import com.youno.app.MyApplication;
import com.youno.fragment.CategoryFragment;
import com.youno.fragment.HomeFragmentFather;
import com.youno.fragment.OrderFragment;
import com.youno.fragment.PersonalFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
	private final static Integer DEFAULT_INDEX = 1;
	protected static final String TAG = "MainActivity";
	private Context mContext;
	private View mPopView;
	private View mCurrentLinnerLayout;
	
	private TextView app_cancle;
	private TextView app_exit;
	private TextView app_change;

	private LinearLayout ll_home;
	private static LinearLayout ll_category;
	private LinearLayout ll_order;
	private LinearLayout ll_personal;
	
	private ImageView iv_category;
	private ImageView iv_home;
	private ImageView iv_order;
	private ImageView iv_personal;
	
	private TextView tv_home;
	private TextView tv_category;
	private TextView tv_order;
	private TextView tv_personal;
	
	private PopupWindow mPopupWindow;
	private LinearLayout buttomBarGroup;
	
	/** 
     * 用于对Fragment进行管理 
     */ 
	private FragmentManager fragmentManager = null;
	
	private HomeFragmentFather homeFragmentFather = null;
	private CategoryFragment categoryFragment = null;
	private OrderFragment orderFragment = null;
	private PersonalFragment personalFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        mContext=this;
        fragmentManager = getSupportFragmentManager();
		findView();
		init();
		ll_home.performClick();
	}
	

	private void findView() {
		mPopView=LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
		buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
		
		ll_home = (LinearLayout) findViewById(R.id.ll_home);
		ll_category = (LinearLayout) findViewById(R.id.ll_category);
		ll_order = (LinearLayout) findViewById(R.id.ll_order);
		ll_personal = (LinearLayout) findViewById(R.id.ll_personal);
		
		iv_home = (ImageView) findViewById(R.id.iv_home);
		iv_category = (ImageView) findViewById(R.id.iv_category);
		iv_order = (ImageView) findViewById(R.id.iv_order);
		iv_personal = (ImageView) findViewById(R.id.iv_personal);
		
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_category = (TextView) findViewById(R.id.tv_category);
		tv_order = (TextView) findViewById(R.id.tv_order);
		tv_personal = (TextView) findViewById(R.id.tv_personal);
		
		app_cancle=(TextView) mPopView.findViewById(R.id.app_cancle);
		app_change=(TextView) mPopView.findViewById(R.id.app_change_user);
		app_exit=(TextView) mPopView.findViewById(R.id.app_exit);
		
	}


    private void init() {
		ll_home.setOnClickListener(this);
		ll_category.setOnClickListener(this);
		ll_order.setOnClickListener(this);
		ll_personal.setOnClickListener(this);
		
        mPopupWindow=new PopupWindow(mPopView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		
		app_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		
		app_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, CityActivity.class);
				startActivity(intent);
				((Activity)mContext).overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				finish();
			}
		});
		
		app_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
    
    @Override
	public void onClick(View view) {
    	FragmentTransaction transaction = fragmentManager.beginTransaction();
    	// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
        hideFragments(transaction);  
		switch (view.getId()) {
		case R.id.ll_home:
			selectedBottomTab(Constant.HOME);
			if(homeFragmentFather == null) {
				homeFragmentFather = new HomeFragmentFather();
				transaction.add(R.id.fl_content, homeFragmentFather, MainActivity.TAG);
			}else{
				transaction.show(homeFragmentFather);
			}
			break;
		case R.id.ll_category:
			selectedBottomTab(Constant.CATEGORY);
			if(categoryFragment == null) {
				categoryFragment = new CategoryFragment();
				transaction.add(R.id.fl_content, categoryFragment, MainActivity.TAG);
			}else{
				transaction.show(categoryFragment);
			}		
		    break;
		case R.id.ll_order:
			selectedBottomTab(Constant.ORDER);
			if(orderFragment == null) {
				orderFragment = new OrderFragment();
				transaction.add(R.id.fl_content, orderFragment, MainActivity.TAG);
			}else{
				transaction.show(orderFragment);
			}
			break;
		case R.id.ll_personal:
			selectedBottomTab(Constant.PERSONAL);
			if(personalFragment == null) {
				personalFragment = new PersonalFragment();
				transaction.add(R.id.fl_content, personalFragment, MainActivity.TAG);
			}else{
				transaction.show(personalFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}
    
    /** 
     * 将所有的Fragment都置为隐藏状态。 
     *  
     * @param transaction 
     *            用于对Fragment执行操作的事务 
     */  
    private void hideFragments(FragmentTransaction transaction) { 
    	if(homeFragmentFather != null){
    		transaction.hide(homeFragmentFather);
    	}
    	if(categoryFragment != null){
    		transaction.hide(categoryFragment);
    	}
    	if(orderFragment != null){
    		transaction.hide(orderFragment);
    	}
    	if(personalFragment != null){
    		transaction.hide(personalFragment);
    	}
    }
	
	/**
	 * tab条图片切换
	 * 
	 * @param paramViewId
	 */
	protected void selectedBottomTab(int paramViewId) {
		this.iv_home.setBackgroundResource(R.drawable.bottom_tab_home_normal);
		this.iv_category.setBackgroundResource(R.drawable.bottom_tab_category_normal);
		this.iv_order.setBackgroundResource(R.drawable.bottom_tab_order_normal);
		this.iv_personal.setBackgroundResource(R.drawable.bottom_tab_personal_normal);
		
		this.tv_home.setTextColor(this.getResources().getColor(R.color.dark_grey));
		this.tv_category.setTextColor(this.getResources().getColor(R.color.dark_grey));
		this.tv_order.setTextColor(this.getResources().getColor(R.color.dark_grey));
		this.tv_personal.setTextColor(this.getResources().getColor(R.color.dark_grey));
		
		switch (paramViewId) {
		case Constant.HOME:
			this.iv_home.setBackgroundResource(R.drawable.bottom_tab_home_check);
			this.tv_home.setTextColor(this.getResources().getColor(R.color.sky_blue));
			Constant.defaultIndex = Constant.HOME;
			setButton(ll_home);
			break;
		case Constant.CATEGORY:
			this.iv_category.setBackgroundResource(R.drawable.bottom_tab_category_check);
			this.tv_category.setTextColor(this.getResources().getColor(R.color.sky_blue));
			Constant.defaultIndex = Constant.CATEGORY;
			setButton(ll_category);
			break;
		case Constant.ORDER:
			this.iv_order.setBackgroundResource(R.drawable.bottom_tab_order_check);
			this.tv_order.setTextColor(this.getResources().getColor(R.color.sky_blue));
			Constant.defaultIndex = Constant.ORDER;
			setButton(ll_order);
			break;
		case Constant.PERSONAL:
			this.iv_personal.setBackgroundResource(R.drawable.bottom_tab_personal_check);
			this.tv_personal.setTextColor(this.getResources().getColor(R.color.sky_blue));
			Constant.defaultIndex = Constant.PERSONAL;
			setButton(ll_personal);
			break;
		}
	}
	
	private void setButton(View view){
		if(mCurrentLinnerLayout!=null && mCurrentLinnerLayout.getId()!=view.getId()){
			mCurrentLinnerLayout.setEnabled(true);
		}
		view.setEnabled(false);
		mCurrentLinnerLayout = view;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_MENU){
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
			mPopupWindow.showAtLocation(buttomBarGroup, Gravity.BOTTOM, 0, 0);
			mPopupWindow.setAnimationStyle(R.style.app_pop);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.update();
		}
		return super.onKeyDown(keyCode, event);
		
	}
	
	public static void showCategoryFragment(){
		ll_category.performClick();
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			// 具体的操作代码
			Log.e("hjq", "onBackPressed");
			DialogUtil.showNoTitleDialog(MainActivity.this,
					R.string.system_sureifexit, R.string.app_exit_user, R.string.app_cancle,
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							MyApplication.getInstance().exit();
						}

						
					}, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					}, true);
		}
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.getInstance().removeActivity(this);
	}

}
