package com.youno.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import com.youno.R;
import com.youno.model.shopItemInfo;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

public class shopListAdapter extends BaseAdapter {
	
	private Context context = null;
	private AsyncBitmapLoader asyncLoader = null;
	List<shopItemInfo> shop_list;
	LayoutInflater inflater;
	private Handler mHandle;
	public final static String BUNDLE_KEY_DATA = "shopiteminfo";
	private static int position = 0;

	public shopListAdapter(Context context, List<shopItemInfo> shop_list) {
		this.context = context;
		this.shop_list = shop_list;
		this.asyncLoader = new AsyncBitmapLoader();
		this.inflater = LayoutInflater.from(context);
	}
	
	public shopListAdapter(Context context,Handler handler, List<shopItemInfo> shop_list) {
		this.context = context;
		this.mHandle = handler;
		this.shop_list = shop_list;
		this.asyncLoader = new AsyncBitmapLoader();
		this.inflater = LayoutInflater.from(context);
	}

	public void onDateChange(List<shopItemInfo> shop_list) {
		this.shop_list = shop_list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return shop_list.size();
	}

	@Override
	public Object getItem(int position) {
		return shop_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		shopItemInfo entity = shop_list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.common_shop_list_item, null);
			holder.shop_name_tv = (TextView) convertView
					.findViewById(R.id.tv_store_name);
			holder.score_tv = (TextView) convertView
					.findViewById(R.id.tv_shop_score);
			holder.nearestArea_tv = (TextView) convertView
					.findViewById(R.id.tv_area_nearest);
			holder.nearestArea_tv = (TextView) convertView
					.findViewById(R.id.tv_area_nearest);
			holder.shopLogo_iv = (ImageView) convertView
					.findViewById(R.id.iv_store_logo);
			holder.activtyDes_tv = (TextView) convertView
					.findViewById(R.id.tv_activity_des);
			holder.standardPrice_tv = (TextView) convertView
					.findViewById(R.id.tv_standard_price);
			holder.discountPrice_tv = (TextView) convertView
					.findViewById(R.id.tv_discount_price);
			holder.saledAmount_tv = (TextView) convertView
					.findViewById(R.id.tv_saled_amount);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.shop_name_tv.setText(entity.getShopName());
		holder.score_tv.setText(Double.toString(entity.getScore())+"分");
		holder.nearestArea_tv.setText(entity.getNearestArea());
		holder.activtyDes_tv.setText(entity.getActivtyDes());
		holder.standardPrice_tv.setText(Double.toString(entity.getStandardPrice())+"元");
		holder.discountPrice_tv.setText(Double.toString(entity.getDiscountPrice())+"元");
		holder.discountPrice_tv.getPaint().setAntiAlias(true);//抗锯齿
		holder.discountPrice_tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		holder.saledAmount_tv.setText("已售"+Integer.toString(entity.getSaledAmount())+"张");
		
		ImageView imageView = holder.shopLogo_iv;
		imageView.setScaleType(ScaleType.FIT_XY);
		
		Bitmap bitmap = asyncLoader.loadBitmap(imageView,   
				entity.getShopLogoUrl(),  
	            new ImageCallBack() {  
	                @Override  
	                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
	                    imageView.setImageBitmap(bitmap);  
	                }  
	            });  
	          
	    if(bitmap == null)  
	    {  
	        imageView.setImageResource(R.drawable.ic_launcher);  
	    }  
	    else  
	    {  
	        imageView.setImageBitmap(bitmap);  
	    }
	    
	    //设置点击事件
	    convertView.setOnClickListener(new OnItemClickListener(position));
	    
		return convertView;
	}

	class ViewHolder {
		TextView shop_name_tv;
		TextView score_tv;
		TextView nearestArea_tv;
		ImageView shopLogo_iv;
		TextView activtyDes_tv;
		TextView standardPrice_tv;
		TextView discountPrice_tv;
		TextView saledAmount_tv;
	}
	
	private class OnItemClickListener implements View.OnClickListener {
		// 点击列表位置
		private int position;
		
		public OnItemClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// 创建Message并填充数据，通过mHandle联系Activity接收处理
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putSerializable(BUNDLE_KEY_DATA, shop_list.get(position));
			msg.setData(bundle);
			mHandle.sendMessage(msg);
		}
	}
}
