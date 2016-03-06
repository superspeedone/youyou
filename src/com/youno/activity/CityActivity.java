package com.youno.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.youno.adapter.CityAdapter;
import com.youno.fragment.HomeFragmentFather;
import com.youno.view.TitleBarView;
import com.youno.widget.BaiduMapControl;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.youno.R;
import com.yunno.utils.PreferenceUtil;

public class CityActivity extends Activity implements OnItemClickListener {
	protected static final String TAG = "CityActivity";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private BaiduMapControl mBaiduMapControl;
	private StickyListHeadersListView mCityList;
	private CityAdapter mCityAdapter;
	private ArrayList<String> mCityDataList;// 已开通城市列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		mContext = getApplicationContext();
		findView();
		initTitleView();
		init();
	}

	private void findView() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mBaiduMapControl = new BaiduMapControl();
	}

	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE,
				View.GONE, View.GONE);
		mTitleBarView.setBtnLeft(R.drawable.icon_back, R.string.activity_city);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}

	private void init() {
		mBaiduMapControl.Location(getApplicationContext(),
				new BDLocationListener() {
					@Override
					public void onReceiveLocation(BDLocation location) {
						String city = location.getCity(); // 获取城市
						// 把当前城市写入配置缓存
						PreferenceUtil.getInstance(mContext).setString("city", city);
						PreferenceUtil.getInstance(mContext).setString("address", location.getAddrStr());
						Toast.makeText(CityActivity.this, city,
								Toast.LENGTH_LONG).show();
						// 开通服务城市
						mCityDataList = new ArrayList<String>();
						mCityDataList.add(city);
						mCityDataList.add("南昌");
						mCityDataList.add("北京");
						mCityDataList.add("上海");
						mCityDataList.add("广州");
						mCityDataList.add("深圳");
						mCityDataList.add("崇仁");

						mCityList = (StickyListHeadersListView) findViewById(R.id.list_city);
						mCityList.setOnItemClickListener(CityActivity.this);
						mCityAdapter = new CityAdapter(CityActivity.this,
								mCityDataList);
						mCityList.setAdapter(mCityAdapter);
						mBaiduMapControl.stopLocation();
					}

				});
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long resId) {
		//HomeFragmentFather.setCityLoaction(mCityDataList.get(position));
		Bundle bundle = new Bundle();
		bundle.putString("citylocation", mCityDataList.get(position));
		finish();
	}

}
