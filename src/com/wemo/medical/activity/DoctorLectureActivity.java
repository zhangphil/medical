package com.wemo.medical.activity;

import java.util.HashMap;
import java.util.Map;

import com.wemo.medical.R;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;



@SuppressLint("JavascriptInterface")
public class DoctorLectureActivity extends Activity implements OnClickListener{
	
	
	private ImageView back;
	private WebView webview;
	Map<String, String> token = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_doctor_lecture);
		
		
		initView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView()
	{
		webview=(WebView)findViewById(R.id.webview_doctor_lecture);
		back=(ImageView)findViewById(R.id.doctor_lecture_img_back);
		back.setOnClickListener(this);
		MedicalApplication application = (MedicalApplication) getApplication();
		CustomerInfo cusInfo = application.getCustomerInfo();
		String authorization = "Basic " + cusInfo.getToken();
		token.put("Authorization", authorization);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(true);
		//webview.getSettings().setBlockNetworkImage(false);
		webview.getSettings().setLoadsImagesAutomatically(true);
		String url = Constants.BASE_URL + "/param/cmsroot";
		Log.d("SurveyDetailActivty", "url:  " + url);
		Log.d("SurveyDetailActivty", "token:  " + token);
		
		
		webview.addJavascriptInterface(this, "demo");
		
		
		webview.setWebViewClient(new WebViewClient(){

		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        // TODO Auto-generated method stub
		        webview.loadUrl(url,token);
		        return true;
		      }
		    });
		webview.loadUrl(url);
		
	}
	
/**
 * 柴源在这个函数里加你想要跳转的地址，webview.loadUrl(param url);
 * 可以加个判断，如果要返回主界面则startActivity
 * @param id
 */
	public void startPhone(String paramUrl)
	{
		System.out.println("123");
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.doctor_lecture_img_back:
			this.finish();
			ActivityStartAnim.TopToBottom(this);
			break;
		default:
			break;
		}
		
	}
	
}