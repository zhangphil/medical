package com.wemo.medical.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.wemo.medical.R;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

@SuppressLint("SetJavaScriptEnabled")
public class RiskReportActivity extends Activity implements OnClickListener {

	private ImageView back;
	private WebView web;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private Map<String, String> token;
	private String reportId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_risk_report);

		initView();
		initMembers();
	}

	private void initView() {
		back = (ImageView) this.findViewById(R.id.risk_report_img_back);
		web = (WebView) this.findViewById(R.id.risk_report_web);

		web.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress > 50) {
					waitDialog.dismiss();
				}
			}
		});
		web.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});

		back.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (web != null) {

			web.removeAllViews();

			web.destroy();
		}
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		String authorization = "Basic " + getIntent().getStringExtra("token");
		token = new HashMap<String, String>();
		token.put("Authorization", authorization);
		reportId = getIntent().getStringExtra("id");

		WebSettings webSettings = web.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		web.loadUrl(Constants.BASE_URL + "/mobile/single/report/detail?id="
				+ reportId + "&authcType=from_authc", token);
		// web.loadUrl("http://echarts.baidu.com/");
		showDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.risk_report_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		default:
			break;
		}
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(RiskReportActivity.this,
				view);
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
}
