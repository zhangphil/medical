package com.wemo.medical.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.wemo.medical.R;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

@SuppressLint("SetJavaScriptEnabled")
public class ExamDetailActivity extends Activity implements OnClickListener {

	private ImageView back;
	private WebView web;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private String examId;
	private String examNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exam_detail);

		initView();
		initMembers();
	}

	private void initView() {
		back = (ImageView) this.findViewById(R.id.exam_detail_img_back);
		web = (WebView) this.findViewById(R.id.exam_detail_web);

		back.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		String authorization = "Basic " + cusInfo.getToken();
		Map<String, String> token = new HashMap<String, String>();
		token.put("Authorization", authorization);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSupportZoom(true);
		examId = getIntent().getStringExtra("examId");
		examNo = getIntent().getStringExtra("examNo");
		String url = Constants.BASE_URL + "/mobile/single/exam/detail?id="
				+ examId + "&examNo=" + examNo;
		Log.d("SurveyDetailActivty", "url:  " + url);
		Log.d("SurveyDetailActivty", "token:  " + token);
		web.loadUrl(url, token);
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

		web.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exam_detail_img_back:
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

		waitDialog = Constants.createLoadingDialog(ExamDetailActivity.this,
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
