package com.youno.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.youno.R;
import com.youno.model.shopItemInfo;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

public class homeShopListAdapter extends BaseAdapter {
	
	private Context context = null;
	private AsyncBitmapLoader asyncLoader = null;
	List<shopItemInfo> shop_list;
	LayoutInflater inflater;

	public homeShopListAdapter(Context context, List<shopItemInfo> shop_list) {
		this.context = context;
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
			convertView = inflater.inflate(R.layout.home_shop_list_item, null);
			holder.shop_name_tv = (TextView) convertView
					.findViewById(R.id.tv_shop_name);
			holder.shopLogo_iv = (ImageView) convertView
					.findViewById(R.id.iv_store_logo);
			holder.shop_des_tv = (TextView) convertView
					.findViewById(R.id.tv_shop_description);
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
		holder.shop_des_tv.setText(entity.getNearestArea()+"   "+entity.getActivtyDes());
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
		return convertView;
	}

	class ViewHolder {
		TextView shop_name_tv;
		TextView shop_des_tv;
		ImageView shopLogo_iv;
		TextView standardPrice_tv;
		TextView discountPrice_tv;
		TextView saledAmount_tv;
	}
}
