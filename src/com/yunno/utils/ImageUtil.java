package com.yunno.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import android.R.integer;
import android.graphics.PorterDuff;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class ImageUtil {
	private static final String SDCARD_CACHE_IMG_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/redbaby/images/";
	protected static final String TAG = "ImageUtil";

	/**
	 * 从网络中获取图片流
	 * 
	 * @param imageURL
	 * @return InputStream
	 */

	public static InputStream getStreamFromURL(String imageURL) {
		URL url;
		InputStream in = null;
		try {
			url = new URL(imageURL);
			URLConnection conn = url.openConnection();
			conn.connect();
			in = conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 从网络中获取图片输入缓冲流
	 * 
	 * @param imageURL
	 * @return BufferedInputStream
	 */

	public static BufferedInputStream getBufferedStreamFromURL(String imageURL) {
		URL url;
		BufferedInputStream bis = null;
		try {
			url = new URL(imageURL);
			URLConnection conn = url.openConnection();
			conn.connect();
			bis = new BufferedInputStream(conn.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bis;
	}

	/**
	 * 保存图片到SD卡
	 * 
	 * @param imagePath
	 * @param byte[] buffer
	 * @throws IOException
	 */
	public static void saveImage(String imagePath, byte[] buffer)
			throws IOException {
		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdir();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		}
	}

	/**
	 * 从SD卡加载图图片
	 * 
	 * @param String
	 *            imagePath
	 * @return Bitmap
	 */
	public static Bitmap getImageFromLocal(String imagePath) {
		File file = new File(imagePath);
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			file.setLastModified(System.currentTimeMillis());
			return bitmap;
		}
		return null;
	}

	/**
	 * 异步加载网络图片
	 * 
	 * @return Bitmap
	 * @throws IOException
	 */
	public static Bitmap loadImage(final String imagePath, final String imgUrl,
			final ImageCallback callback) {
		Bitmap bitmap = getImageFromLocal(imagePath);
		if (bitmap != null) {
			return bitmap;
		} else {// 从网上加加载图片
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.obj != null) {
						Bitmap bitmap = (Bitmap) msg.obj;
						callback.loadImage(bitmap, imagePath);
					}
				}
			};

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						URL url = new URL(imgUrl);
						URLConnection conn = url.openConnection();
						conn.connect();
						BufferedInputStream bis = new BufferedInputStream(
								conn.getInputStream(), 8192) {
						};
						Bitmap bitmap = BitmapFactory.decodeStream(bis);
						Message msg = handler.obtainMessage();
						msg.obj = bitmap;
						handler.sendMessage(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			ThreadPoolManager.getInstance().addTask(runnable);
		}
		return null;
	}

	/**
	 * 返回图片存到sd卡的路径
	 * 
	 * @return sd卡的路径
	 */
	public static String getCacheImgPath() {
		return SDCARD_CACHE_IMG_PATH;
	}

	public static String md5(String paramString) {
		String returnStr;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return paramString;
		}
	}

	/**
	 * 将指定byte数组转换成16进制字符串
	 * 
	 * @param byte[]
	 * @return 16进制字符串
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * 回调函数接口
	 * 
	 */
	public interface ImageCallback {
		public void loadImage(Bitmap bitmap, String imagePath);
	}

	/**
	 * bitmap 正方形图片变圆形图片，带边框
	 * 
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap,int frameColor, int frameWidth) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2 - 5;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2 - 5;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst_left + 15, dst_top + 15,
				dst_right - 20, dst_bottom - 20);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		// 画上白边
		// 抗锯齿
		paint.setFilterBitmap(true);
		paint.setDither(true);

		paint.setColor(frameColor);
		paint.setStrokeWidth(frameWidth);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(height / 2 - 2, height / 2 - 2, width / 2 - 20, paint);

		return output;
	}

	/**
	 * bitmap 绘制圆角矩形
	 * 
	 * @param x
	 *            宽度
	 * @param y
	 *            高度
	 * @param image
	 *            bitmap图像
	 * @param outerRadiusRat
	 *            圆角大小
	 * @return Bitmap
	 */

	public static Bitmap createRangelRoundPhoto(int x, int y, Bitmap image,
			float outerRadiusRat) {
		// 根据源文件新建一个darwable对象
		Drawable imageDrawable = new BitmapDrawable(image);

		// 新建一个新的输出图片
		Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, x, y);

		// 产生一个红色的圆角矩形
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// 将源图片绘制到这个圆角矩形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, x, y);
		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();

		return output;
	}
	
	public static void bitmapRecyle(Bitmap bitmap){
		//回收内存中的bitmap对象，释放内存空间
		if(bitmap!= null && !bitmap.isRecycled()){
			// 回收并且置为null
			bitmap.recycle();
			bitmap= null;
		}
        System.gc();
	}
}
