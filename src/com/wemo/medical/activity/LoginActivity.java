package com.wemo.medical.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.db.SqliteHelper;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

@SuppressLint("HandlerLeak")
public class LoginActivity extends Activity implements OnClickListener {

	private Context context;
	private CustomerInfo customerInfo;
	private TextView login;
	private TextView forgetPassword;
	private RelativeLayout register;
	private EditText handsetNo;
	private EditText password;
	private Intent intent;
	private SharedPreferences preferences;
	private Editor editor;
	private RequestHttp http;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		initView();
		remeberAccount();
		
		
	
		
		
	}

	/**
	 * 初始化控件和成员变量
	 */
	private void initView() {
		context = LoginActivity.this;
		//创建SharedPreferences实例，实例名称为medical，读写方式私有，其中 MODE_PRIVATE为0x00
		preferences = context.getSharedPreferences("medical", 0);
		handsetNo = (EditText) this.findViewById(R.id.login_et_phone);
		password = (EditText) this.findViewById(R.id.login_et_password);
		login = (TextView) this.findViewById(R.id.login_btn_login);
		register = (RelativeLayout) this.findViewById(R.id.login_rl_register);
		forgetPassword = (TextView) this
				.findViewById(R.id.login_btn_forget_password);

		login.setOnClickListener(this);
		register.setOnClickListener(this);
		forgetPassword.setOnClickListener(this);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login:
			login();
			break;

		case R.id.login_btn_forget_password:
			Constants.showToast(context, "暂未开放此功能！");
			break;

		case R.id.login_rl_register:
			Constants.showToast(context, "暂未开放此功能！");
			break;

		default:
			break;
		}
	}

	/**
	 * 点击登录按钮后的操作
	 */
	private void login() {
		String sHandsetNo = handsetNo.getText().toString();
		String sPassword = password.getText().toString();
		http = RequestHttp.getInstance(context);
		LoginHandler loginHandler = new LoginHandler();
		//我想查看requestLogin的数据交互过程
		http.requestLogin(loginHandler, sHandsetNo, sPassword);
		showDialog();
	}

	/**
	 * 登录请求Handler
	 * 
	 */
	private class LoginHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				customerInfo = (CustomerInfo) msg.obj;
				saveAccountToPreferences();
				saveAccountToApplication();
				saveAccountToDatebase();
				submitBaiduPush(customerInfo.getId());

				intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				ActivityStartAnim.DownToUp(LoginActivity.this);
				waitDialog.dismiss();
				LoginActivity.this.finish();
				break;

			case RequestHttp.REQUEST_FAILER:
				waitDialog.dismiss();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						"login");

			default:
				break;
			}
		}
	}

	/**
	 * 记住登录账户名和密码
	 */
	private void remeberAccount() {
		handsetNo.setText(preferences.getString("handsetNo", null));
		password.setText(preferences.getString("password", null));
	}

	/**
	 * 将登录成功后的用户名和密码保存到sharedPreferences
	 */
	private void saveAccountToPreferences() {
		editor = preferences.edit();
		editor.putString("handsetNo", handsetNo.getText().toString());
		editor.putString("password", password.getText().toString());
		editor.commit();
	}

	private void submitBaiduPush(String cusId) {
		String userId = preferences.getString("userId", null);
		String channelId = preferences.getString("channelId", null);
		Log.d("LoginActivity", userId + "  " + channelId + " " + cusId);
		http.requestSubmitBaiduPush(cusId, userId, channelId);
	}

	/**
	 * 将客户登录信息保存到application
	 */
	private void saveAccountToApplication() {
		MedicalApplication application = (MedicalApplication) getApplication();
		application.setCustomerInfo(customerInfo);
		Log.d("LoginActivity", application.getCustomerInfo().getCusName());
	}

	/**
	 * 将客户登录信息保存到数据库，用于登录成功且未注销的用户不用再次登录
	 */
	private void saveAccountToDatebase() {
		SqliteHelper sqliteHelper = new SqliteHelper(context);
		sqliteHelper.insertCustomerInfo(customerInfo);
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(LoginActivity.this, view);
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
