package com.wemo.medical.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

@SuppressLint("SetJavaScriptEnabled")
public class SurveyActivity extends Activity implements OnClickListener {

	private ImageView back;
	private WebView web;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private Context context;
	public static Handler finishHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_survey);

		initView();
		setHandler();

		// 加载提示框
		showDialogTips();
	}

	private void initView() {
		context = SurveyActivity.this;
		Map<String, String> token = new HashMap<String, String>();
		back = (ImageView) this.findViewById(R.id.survey_img_back);
		web = (WebView) this.findViewById(R.id.survey_webview);
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		String authorization = "Basic " + cusInfo.getToken();
		token.put("Authorization", authorization);
		back.setOnClickListener(this);
		Log.d("SurveyActivity", token.toString());
		Log.d("SurveyActivity", cusInfo.getId());
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSupportZoom(true);
		web.loadUrl(Constants.BASE_URL + "/mobile/single/risk/form?cid="
				+ cusInfo.getId() + "&authcType=from_authc", token);
		showDialog();
		web.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					waitDialog.dismiss();
				}
			}
		});
		// web.setWebViewClient(new WebViewClient() {
		//
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		//
		// });
	}

	private void setHandler() {
		finishHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 200) {
					SurveyActivity.this.finish();
					ActivityStartAnim.LeftToRightClose(SurveyActivity.this);
				}
			}
		};
	}

	@Override
	public boolean onKeyDown(int keyCoder, KeyEvent event) {
		if (web.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
			web.goBack(); // goBack()表示返回webView的上一页面
			return true;
		} else {
			SurveyTipsDialog dialog = new SurveyTipsDialog(context);
			dialog.show();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.survey_img_back:
			SurveyTipsDialog dialog = new SurveyTipsDialog(context);
			dialog.show();
			break;

		default:
			break;
		}
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(SurveyActivity.this, view);
		waitDialog.show();
		Object obj = iv_loading.getBackground();
		anim = (AnimationDrawable) obj;
		anim.stop();
		anim.start();
	}

	private View view;
	private LayoutInflater inflater;
	private ImageView iv_loading;
	private Dialog waitDialog;
	private AnimationDrawable anim = null;
	private TextView button_tips;

	/**
	 * @param 提示对话框2015
	 *            -3
	 */

	private void showDialogTips() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_survey_write_tips, null);
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();
		alertDialog.setView(view);
		button_tips = (TextView) view
				.findViewById(R.id.dialog_survery_write_tips_btn);
		button_tips.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
		// 点击屏幕外不会退出dialog
		alertDialog.setCanceledOnTouchOutside(false);
		// 点击返回键不会退出dialog
		alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;// 默认返回 false，这里false不能屏蔽返回键，改成true就可以了
			}
		});
		alertDialog.show();
	}
}
