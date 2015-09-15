package com.wemo.medical.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wemo.medical.R;
import com.wemo.medical.activity.MainActivity;

@SuppressLint("SimpleDateFormat")
public final class Constants {
	// public final static String BASE_URL =
	// "http://192.168.2.101:8081/medical";
	// public final static String BASE_URL =
	// "http://192.168.137.1:8080/medical";
	// public final static String BASE_URL = "http://medical.lanbaoo.com";
	// public final static String BASE_URL =
	// "http://192.168.1.150:8080/medical";
	 //public final static String BASE_URL = "http://192.168.1.106:8080/hmsp";
	 //public final static String BASE_URL = "http://192.168.1.6:8080/hmsp";
	//public final static String BASE_URL = "http://192.168.1.111:8080/hmsp";
	public final static String BASE_URL = "http://218.89.221.30:8888/hmsp";
	//public final static String BASE_URL = "http://127.0.0.1:8080/hmsp/";
	/**
	 * 推送启动界面表示； 1：表示启动"投诉中心"界面 2:表示启动"体检报告"界面 3：表示启动"健管历史"界面
	 */
	public static int PUSH_TO_WHICH_ACTIVITY = 0;

	@SuppressLint("ShowToast")
	public static void showToast(Context context, String content) {
		Toast.makeText(context, content, 0).show();
	}

	/**
	 * 
	 * @param currentActivity
	 *            当前activity
	 * @param nextActivity
	 *            要跳转到的activity
	 * @param type
	 *            新界面启动动画方式 1：从下到上 2：从上到下 3：从右到左 4：从左到右
	 * @param isFinishCurrentContext
	 *            是否结束当前界面
	 */
	public static void goToActivity(Activity currentActivity,
			Class<MainActivity> nextActivity, int type,
			boolean isFinishCurrentContext) {
		Intent intent = new Intent();
		intent.setClass(currentActivity, nextActivity);
		switch (type) {
		case 1:
			ActivityStartAnim.BottomToTop(currentActivity);
			break;

		case 2:
			ActivityStartAnim.TopToBottom(currentActivity);
			break;

		case 3:
			ActivityStartAnim.RightToLeft2(currentActivity);
			break;

		case 4:
			ActivityStartAnim.LeftToRightClose(currentActivity);
			break;

		default:
			break;
		}
		if (isFinishCurrentContext) {
			currentActivity.finish();
		}
		currentActivity.startActivity(intent);
	}

	@SuppressLint("InlinedApi")
	public static Dialog createLoadingDialog(Context context, View view) {
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		return loadingDialog;
	}

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
