package com.youno.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.youno.R;
import com.youno.adapter.OrderListAdapter;
import com.youno.model.OrderItemInfo;
import com.youno.test.TestData;
import com.youno.view.TitleBarView;


public class OrderActivity extends Activity {
	protected static final String TAG = "OrderActivity";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ListView mOrderListView;
	private ArrayList<OrderItemInfo> mOrderlist;
	private OrderListAdapter mOrderListAdapter;
	private ProgressBar imageloading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_order);
		mContext = getApplicationContext();
		mOrderlist = new ArrayList<OrderItemInfo>();
		findView();
		initTitleView();
		init();
	}
	
	private void findView() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mOrderListView = (ListView) findViewById(R.id.lv_order_list);
		imageloading = (ProgressBar) findViewById(R.id.pb_store_logo_loading);
	}

	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
				View.GONE, View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.icon_back,R.string.empty_string);
		mTitleBarView.setTitleText(R.string.my_ordr_title);
	}

	private void init() {
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		mOrderlist.clear();
		if(mOrderListAdapter == null){
			mOrderListAdapter = new OrderListAdapter(mContext, mOrderlist);
			mOrderListView.setAdapter(mOrderListAdapter);
		}
		new MyAsyncTask().execute(0);
	}
	
	private class MyAsyncTask extends
			AsyncTask<Integer, Integer, List<OrderItemInfo>> {
		private List<OrderItemInfo> mOrderItemList = new ArrayList<OrderItemInfo>();

		@Override
		protected List<OrderItemInfo> doInBackground(Integer... params) {
			mOrderItemList = TestData.getOrderList();
			return mOrderItemList;
		}

		@Override
		protected void onPostExecute(List<OrderItemInfo> result) {
			super.onPostExecute(result);
			if (!mOrderlist.isEmpty()) {
				mOrderlist.clear();
			}
			if (result != null) {
				for (OrderItemInfo rc : result) {
					mOrderlist.add(rc);
				}
			}
			mOrderListAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

    }
}
