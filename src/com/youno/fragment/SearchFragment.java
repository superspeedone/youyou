package com.youno.fragment;

import com.youno.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFragment extends Fragment {
	private static final String TAG = "SearchFragment";
	private Context mContext;
	private View mBaseView;
	private EditText mSeachText;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mBaseView = inflater.inflate(R.layout.common_search_l, container, false);
		mContext = getActivity();
		findView();
		init();
		return mBaseView;
	}
	
	private void findView() {
		mSeachText = (EditText) mBaseView.findViewById(R.id.tv_search_input);
	}
	
    private void init() {
    	/*mSeachText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
				final String  searchStr = mSeachText.getText().toString().trim();
				if(actionId == EditorInfo.IME_ACTION_SEARCH ||( keyEvent !=null 
						&& keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
					if(searchStr != null && "".equals(searchStr)){
						mSeachText.requestFocus();
						Toast.makeText(mContext, "搜索文本为："+searchStr, Toast.LENGTH_SHORT).show();
						return true;
					}
				}
				return false;
			}
		});*/
    	
    	mSeachText.setOnKeyListener(new EditText.OnKeyListener() {
			
			@Override
			public boolean onKey(View view, int actionId, KeyEvent event) {
				if(actionId == KeyEvent.KEYCODE_ENTER){
					Toast.makeText(mContext, "回车事件被调用", Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});
	}
}
