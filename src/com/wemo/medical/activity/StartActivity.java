package com.wemo.medical.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.wemo.medical.R;
import com.wemo.medical.db.SqliteHelper;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Dictionary;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.push.Utils;
import com.wemo.medical.util.ActivityStartAnim;

@SuppressLint("HandlerLeak")
public class StartActivity extends Activity {
    
 
	private String TAG = "StartActivity";
	private CustomerInfo customerInfo;
	private Context context;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				Utils.getMetaValue(StartActivity.this, "api_key"));
		
		initView();
		
		getDictionary();
		getDatebaseCustomer();
		
		
		isNeedLogin();
	}

	/**
	 * 初始化成员变量和控件
	 */
	private void initView() {
		context = StartActivity.this;
	}

	/**
	 * 请求字典集
	 */
	private void getDictionary() {
		RequestHttp http = RequestHttp.getInstance(context);
		DictionaryHandler dictionaryHandler = new DictionaryHandler();
		http.requestGetDictionary(dictionaryHandler);
	}
	

	/**
	 * 获取数据库中客户信息
	 */
	private void getDatebaseCustomer() {
		SqliteHelper sqliteHelper = new SqliteHelper(context);
		customerInfo = sqliteHelper.getUser();
	}

	/**
	 * 启动界面后是否需要登录,如果需要登录，跳转到登录界面，若果不需要登录，则跳转到主界面
	 */
	private void isNeedLogin() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					if (customerInfo == null) {
						intent = new Intent(StartActivity.this,
								LoginActivity.class);
					} else {
						MedicalApplication application = (MedicalApplication) getApplication();
						application.setCustomerInfo(customerInfo);
						intent = new Intent(StartActivity.this,
								MainActivity.class);
					}
					startActivity(intent);
					ActivityStartAnim.BottomToTop(StartActivity.this);
					StartActivity.this.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private class DictionaryHandler extends Handler {
		ArrayList<Dictionary> dictionaries;

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				dictionaries = (ArrayList<Dictionary>) msg.obj;
				new Thread(new Runnable() {

					@Override
					public void run() {
						SqliteHelper sqliteHelper = new SqliteHelper(context);
						sqliteHelper.insertDictionary(dictionaries);
					}
				}).start();

				break;
			
			case RequestHttp.REQUEST_FAILER:
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				break;

			default:
				break;
			}
		}
	}
	
    
}
