package com.youno.fragment;

import java.util.ArrayList;
import java.util.List;
import com.youno.AsyncTaskBase;
import com.youno.R;
import com.youno.activity.ShopInfoActivity;
import com.youno.adapter.shopListAdapter;
import com.youno.model.shopItemInfo;
import com.youno.test.TestData;
import com.youno.view.CustomListView;
import com.youno.view.CustomListView.OnLoadMoreListener;
import com.youno.view.TitleBarView;
import com.youno.view.CustomListView.OnRefreshListener;
import com.youno.view.LoadingView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CategoryFragment extends Fragment {
	private static final String TAG = "CategoryFragment";
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private CustomListView mCustomListView;
	private LoadingView mLoadingView;
	shopListAdapter shopListAdapter;
	private List<shopItemInfo> mShopList = new ArrayList<shopItemInfo>();
	private Handler mHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			shopItemInfo shopItemInfo =  (shopItemInfo) msg.getData().getSerializable(shopListAdapter.BUNDLE_KEY_DATA);
			Bundle bundle = new Bundle();                   
			Intent intent = new Intent(mContext, ShopInfoActivity.class);
			bundle.putSerializable("shopiteminfo", shopItemInfo);
			intent.putExtras(bundle);
            startActivity(intent);
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mBaseView = inflater.inflate(R.layout.fragement_category, container, false);
		mContext = getActivity();
		findView();
		initTitleView();
		init();
		return mBaseView;
	}
	
	private void findView() {
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        mCustomListView = (CustomListView) mBaseView.findViewById(R.id.lv_shop_list);
		mLoadingView = (LoadingView) mBaseView.findViewById(R.id.loading);
	}

	private void initTitleView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE, View.GONE,
				View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.bottom_tab_category_check, R.string.menu_category);
		mTitleBarView.setBtnRight(R.drawable.icon_head_search);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Toast.makeText(mContext, "searchActivity", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	private void init() {
		if(shopListAdapter == null){
			shopListAdapter = new shopListAdapter(mContext,mHandle,mShopList);
		}
		mCustomListView.setCanLoadMore(true);
		mCustomListView.setAdapter(shopListAdapter);
		mCustomListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new AsyncRefresh().execute(0);
			}
		});
		mCustomListView.setOnLoadListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				new AsyncRefresh().execute(1);
				
			}
		});
		new ShopItemsAsyncTask(mLoadingView).execute(0);
		/*mCustomListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				Bundle bundle = new Bundle();                   
				Intent intent = new Intent(mContext, ShopInfoActivity.class);
				bundle.putString("datos", view.getTag().toString());
				intent.putExtras(bundle);
                startActivity(intent);
			}
			
		});*/
	}
	
	private class ShopItemsAsyncTask extends AsyncTaskBase {
		List<shopItemInfo> shopItemInfoList = new ArrayList<shopItemInfo>();

		public ShopItemsAsyncTask(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = -1;
			shopItemInfoList = TestData.getShopList();
			
			if (shopItemInfoList.size() > 0) {
				result = 1;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			mShopList.addAll(shopItemInfoList);
			shopListAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private class AsyncRefresh extends
			AsyncTask<Integer, Integer, List<shopItemInfo>> {
		private List<shopItemInfo> mShopItemList = new ArrayList<shopItemInfo>();
		private int REFRESH_WAY = 0;  //0:下拉刷新,1:上拉加载更多

		@Override
		protected List<shopItemInfo> doInBackground(Integer... params) {
			mShopItemList = TestData.getShopList();
			REFRESH_WAY = params[0] > 0 ? 1 : 0;
			return mShopItemList;
		}

		@Override
		protected void onPostExecute(List<shopItemInfo> result) {
			super.onPostExecute(result);
			if (result != null) {
				for (shopItemInfo rc : mShopItemList) {
					mShopList.add(rc);
				}
				shopListAdapter.notifyDataSetChanged();
				if(REFRESH_WAY == 0) {
					mCustomListView.onRefreshComplete();
				}else {
					mCustomListView.onLoadMoreComplete();
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}
}
