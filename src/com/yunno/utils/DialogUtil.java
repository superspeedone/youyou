package com.yunno.utils;


import com.youno.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogUtil {
	
	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "确定", null);
	}
	
	public static void showInfoDialog(Context context, String message, String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}
	
	public static void showDialog(Context context, int titleid, int msgid,
			int leftbtnid, int rightbtnid, OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(false);
		builder.setTitle(titleid);
		builder.setMessage(msgid)
				.setNegativeButton(leftbtnid, LeftOnClickListener)
				.setPositiveButton(rightbtnid, RightOnClickListener).create()
				.show();
	}

	public static void showDialog(Context context, String title, String msg,
			String leftbtn, String rightbtn,
			OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(cancelable);
		builder.setTitle(title).setMessage(msg)
				.setNegativeButton(leftbtn, LeftOnClickListener)
				.setPositiveButton(rightbtn, RightOnClickListener).create()
				.show();
	}

	public static void showNoTitleDialog(Context context, int msgid,
			int leftbtnid, int rightbtnid, OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(cancelable);
		builder.setMessage(msgid)
				.setNegativeButton(leftbtnid, LeftOnClickListener)
				.setPositiveButton(rightbtnid, RightOnClickListener).create()
				.show();
	}

	public static void showNoTitleDialog(Context context, String msg,
			String leftbtn, String rightbtn,
			OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(cancelable);
		builder.setMessage(msg).setNegativeButton(leftbtn, LeftOnClickListener)
				.setPositiveButton(rightbtn, RightOnClickListener).create()
				.show();
	}

	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg 文字显示
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);// 加载布局
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.CustomDialogStyle);// 创建自定义样式dialog
		loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
		loadingDialog.setCanceledOnTouchOutside(false); //点击加载框以外的区域
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		loadingDialog.show();
		
		return loadingDialog;
	}
}
