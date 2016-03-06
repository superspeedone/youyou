package com.yunno.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

/**
 * 文件异步加载，双缓存
 *
 */

public class AsyncImageLoader {
	private Map<String, SoftReference<Bitmap>> imageCaches = null;
	private FileUtil fileUtil;
	private AsyncHttpClient mImageLoader;

	public AsyncImageLoader(Context context) {
		imageCaches = new HashMap<String, SoftReference<Bitmap>>();
		fileUtil = new FileUtil(context);
		mImageLoader = new AsyncHttpClient();
	}

	public boolean DisplayImage(ImageView imageView, String imageUrl, String path/*缓存目录*/) {
		 String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		 String filepath = fileUtil.getAbsolutePath() + "/" + path + filename;
		// 先从软引用中找
		if (imageCaches.containsKey(imageUrl)) {
			SoftReference<Bitmap> reference = imageCaches.get(imageUrl);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
				return true;
			}
		}

		// 从文件中找
		if (fileUtil.isBitmapExists(fileUtil.getAbsolutePath() + "/" + path,filename)) {
			Bitmap bitmap = BitmapFactory.decodeFile(filepath);
			// 重新加入到内存软引用中
			imageCaches.put(imageUrl, new SoftReference<Bitmap>(bitmap));
			imageView.setImageBitmap(bitmap);
			return true;
		}

		// 软引用和文件中都没有再从网络下载
		if (imageUrl != null && !imageUrl.equals("")) 
		{		
			mImageLoader.get(imageUrl,new myBinaryHttpResponseHandler(imageView,imageUrl,filepath) 
			{
				 @Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] binaryData, Throwable error) {
					 Log.e("AsyncImageLoader", "网络加载图片失败"+error.toString());
				}

				@Override
				 public void onSuccess(byte[] arg0) 
				 {
					    InputStream input = new ByteArrayInputStream(arg0); 
					    Bitmap bitmap = BitmapFactory.decodeStream(input);
						if (bitmap != null)
						{
							// 加入到软引用中
							imageCaches.put(getmImageUrl(), new SoftReference<Bitmap>(
									bitmap));
							// 缓存到文件系统
							fileUtil.saveBitmap(getmFilepath(), bitmap);
							getmImageView().setImageBitmap(bitmap);
				        }	
			       }
			  });			
		   }
		
		   return true;
	}
	
	class myBinaryHttpResponseHandler extends BinaryHttpResponseHandler
	{
		private ImageView mImageView;
		private String    mImageUrl;
		private String    mFilepath;
		
		public myBinaryHttpResponseHandler(ImageView imageView,String imageUrl,String filepath)
		{
			mImageView = imageView;
			mImageUrl = imageUrl;
			mFilepath = filepath;
		}
		public ImageView getmImageView() {
			return mImageView;
		}
		public void setmImageView(ImageView mImageView) {
			this.mImageView = mImageView;
		}
		public String getmImageUrl() {
			return mImageUrl;
		}
		public void setmImageUrl(String mImageUrl) {
			this.mImageUrl = mImageUrl;
		}
		public String getmFilepath() {
			return mFilepath;
		}
		public void setmFilepath(String mFilepath) {
			this.mFilepath = mFilepath;
		}	
	}

}
