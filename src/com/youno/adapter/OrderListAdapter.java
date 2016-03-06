package com.youno.adapter;


import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.youno.R;
import com.youno.model.OrderItemInfo;
import com.yunno.utils.AsyncBitmapLoader;
import com.yunno.utils.ImageUtil;
import com.yunno.utils.AsyncBitmapLoader.ImageCallBack;

public class OrderListAdapter extends BaseAdapter {
	
	private Context context = null;
	private AsyncBitmapLoader asyncLoader = null;
	List<OrderItemInfo> orderList;
	LayoutInflater inflater;

	public OrderListAdapter(Context context, List<OrderItemInfo> orderList) {
		this.context = context;
		this.orderList = orderList;
		this.asyncLoader = new AsyncBitmapLoader();
		this.inflater = LayoutInflater.from(context);
	}
	
	public OrderListAdapter(Context context, List<OrderItemInfo> orderList,
			   ProgressBar imageLoading) {
		this.context = context;
		this.orderList = orderList;
		this.asyncLoader = new AsyncBitmapLoader();
		this.inflater = LayoutInflater.from(context);
	}

	public void onDateChange(List<OrderItemInfo> orderList) {
		this.orderList = orderList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return orderList.size();
	}

	@Override
	public Object getItem(int position) {
		return orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		OrderItemInfo entity = orderList.get(position);
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.all_order_list_item, null);
			holder.tv_shop_name = (TextView) convertView
					.findViewById(R.id.tv_shop_name);
			holder.tv_order_status = (TextView) convertView
					.findViewById(R.id.tv_order_state);
			holder.iv_shopLogo = (ImageView) convertView
					.findViewById(R.id.iv_store_logo);
			holder.tv_order_counts = (TextView) convertView
					.findViewById(R.id.tv_order_counts);
			holder.tv_order_date = (TextView) convertView
					.findViewById(R.id.tv_order_date);
			holder.tv_order_total = (TextView) convertView
					.findViewById(R.id.tv_order_total);
			holder.pb_image_loading = (ProgressBar) convertView
					.findViewById(R.id.pb_store_logo_loading);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_shop_name.setText(entity.getShopName());
		holder.tv_order_status.setText(entity.getStatus());
		holder.tv_order_counts.setText(String.valueOf("购买了  "+entity.getOrderCounts()) +"  代金券");
		holder.tv_order_date.setText("创建时间  "+entity.getOrderDate());
		holder.tv_order_total.setText(String.valueOf(entity.getOrderTotal())+"元");

        ImageView imageView = holder.iv_shopLogo;
		imageView.setScaleType(ScaleType.FIT_XY);
		
		Bitmap bitmap = asyncLoader.loadBitmap(imageView,   
				entity.getShopLogoUrl(),  
	            new ImageCallBack() {  
	                @Override  
	                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
	                    imageView.setImageBitmap(ImageUtil.createRangelRoundPhoto(60, 45, bitmap, 10)); 
	                    imageView.setVisibility(View.VISIBLE);
	                    holder.pb_image_loading.setVisibility(View.GONE);
	                }  
	            });  
	          
	    if(bitmap != null)  
	    {  
	        imageView.setImageBitmap(ImageUtil.createRangelRoundPhoto(60, 45, bitmap, 3)); 
	        imageView.setVisibility(View.VISIBLE);
	    }
		return convertView;
	}

	class ViewHolder {
		TextView tv_shop_name;
		TextView tv_order_status;
		ImageView iv_shopLogo;
		TextView tv_order_counts;
		TextView tv_order_total;
		TextView tv_order_date;
		ProgressBar pb_image_loading;
	}
}
