package com.youno.fragment;

import java.util.ArrayList;
import java.util.List;
import com.youno.MainActivity;
import com.youno.R;
import com.youno.adapter.homeShopListAdapter;
import com.youno.model.shopItemInfo;
import com.youno.test.TestData;
import com.youno.view.CustomListView;
import com.youno.view.LoadingView;
import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	private static final String TAG = "HomeFragment";
	private Context mContext;
	private View mBaseView;
	private ListView mGuessLike;
	private TextView mProgress;
	private TextView mCheckAll;
	homeShopListAdapter mHomeShopListAdapter;
	private List<shopItemInfo> mShopList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mBaseView = inflater.inflate(R.layout.fragment_home, container, false);
		mContext = getActivity();
		mShopList = new ArrayList<shopItemInfo>();
		findView();
		initTitleView();
		init();
		return mBaseView;
	}

	private void findView() {
		mGuessLike = (ListView) mBaseView.findViewById(R.id.lv_guess_like);
		mProgress = (TextView) mBaseView.findViewById(R.id.tv_guess_like_progress);
		mCheckAll = (TextView) mBaseView.findViewById(R.id.tv_check_all_shop);
	}

	private void initTitleView() {

	}

	private void init() {
		mShopList.clear();
		if(mHomeShopListAdapter == null){
			mHomeShopListAdapter = new homeShopListAdapter(mContext, mShopList);
			mGuessLike.setAdapter(mHomeShopListAdapter);
		}
		new MyAsyncTask().execute(0);
		
		mCheckAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.showCategoryFragment();
			}
		});
	}
	
	private class MyAsyncTask extends
			AsyncTask<Integer, Integer, List<shopItemInfo>> {
		private List<shopItemInfo> mShopItemList = new ArrayList<shopItemInfo>();
		
		@Override
		protected List<shopItemInfo> doInBackground(Integer... params) {
			if(!mShopItemList.isEmpty()){
				mShopItemList.clear();
			}
			mShopItemList = TestData.getShopList();
			return mShopItemList;
		}

		@Override
		protected void onPostExecute(List<shopItemInfo> result) {
			super.onPostExecute(result);
			mProgress.setVisibility(View.GONE);
			mGuessLike.setVisibility(View.VISIBLE);
			if(!mShopList.isEmpty()){ mShopList.clear(); }
			if (result != null) {
			   for (shopItemInfo rc : result) {
					mShopList.add(rc);
			   }
			}
			mHomeShopListAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			mProgress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

	}
}
