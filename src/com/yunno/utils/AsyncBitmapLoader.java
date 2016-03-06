package com.yunno.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import com.yunno.utils.ImageUtil;

/**
 * @author zhanshen
 * 图片异步加载，双缓冲机制
 **/
public class AsyncBitmapLoader {

	private static final String TAG = "AsyncBitmapLoader";
	/**
	 * 内存图片软引用缓冲
	 */
	private HashMap<String, SoftReference<Bitmap>> imageCache = null;

	public AsyncBitmapLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public Bitmap loadBitmap(final ImageView imageView, final String imageURL,
			final ImageCallBack imageCallBack) {
		// 在内存缓存中，则返回Bitmap对象
		if (imageCache.containsKey(imageURL)) {
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		} else {
			/**
			 * 加上一个对本地缓存的查找
			 */
			String bitmapName = imageURL
					.substring(imageURL.lastIndexOf("/") + 1);
			File cacheDir = null;
			File[] cacheFiles = null;
			if (FileUtil.isExternalStorageWritable()) {
				cacheDir = new File(Environment.getExternalStorageDirectory()
						+ "/younoCache/");
				if (!cacheDir.exists()) {
					cacheDir.mkdirs();
					cacheFiles = cacheDir.listFiles();
				}
			} else {
				Log.i(TAG, "SD卡不可用，读取图片失败");
			}

			if (cacheFiles != null && cacheFiles.length > 0) {
				int i = 0;
				for (; i < cacheFiles.length; i++) {
					if (bitmapName.equals(cacheFiles[i].getName())) {
						break;
					}
				}

				if (i < cacheFiles.length) {
					return BitmapFactory.decodeFile(Environment
							.getExternalStorageDirectory()
							+ "/younoCache/"
							+ bitmapName);
				}
			}
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				imageCallBack.imageLoad(imageView, (Bitmap) msg.obj);
			}
		};

		// 如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
		new Thread() {
			@Override
			public void run() {
				InputStream bitmapIs = ImageUtil.getStreamFromURL(imageURL);

				Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
				imageCache.put(imageURL, new SoftReference<Bitmap>(bitmap));
				Message msg = handler.obtainMessage(0, bitmap);
				handler.sendMessage(msg);

				File dir = null;

				if (FileUtil.isExternalStorageWritable()) {
					dir = new File(Environment.getExternalStorageDirectory()
							+ "/younoCache/");
					if (!dir.exists()) {
						dir.mkdirs();
						File bitmapFile = new File(
								Environment.getExternalStorageDirectory()
										+ "/younoCache/"
										+ imageURL.substring(imageURL
												.lastIndexOf("/") + 1));
						if (!bitmapFile.exists()) {
							try {
								bitmapFile.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						FileOutputStream fos;
						try {
							fos = new FileOutputStream(bitmapFile);
							if(imageURL.substring(imageURL.lastIndexOf(".")+1).toUpperCase().equals("PNG")){
								bitmap.compress(Bitmap.CompressFormat.PNG, 100,fos);
							}else if(imageURL.substring(imageURL.lastIndexOf(".")+1).toUpperCase().equals("JPG")){
								bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fos);
							}
							fos.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					Log.i(TAG, "SD卡不可用，保存图片失败");
				}
			}
		}.start();

		return null;
	}

	/**
	 * 回调接口
	 * 
	 * @author onerain
	 * 
	 */
	public interface ImageCallBack {
		public void imageLoad(ImageView imageView, Bitmap bitmap);
	}
}
