package com.wemo.medical.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

@SuppressLint("HandlerLeak")
public class ChangePasswordActivity extends Activity implements OnClickListener {

	private Context context;
	private ImageView back;
	private TextView save;
	private EditText oldPassword;
	private EditText newPassword;
	private EditText confirmPassword;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private RequestHttp http;
	private String sOld;
	private String sNew;
	private String sConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_change_password);

		initView();
		initMembers();
	}

	private void initView() {
		context = ChangePasswordActivity.this;
		back = (ImageView) this.findViewById(R.id.change_password_img_back);
		save = (TextView) this.findViewById(R.id.change_password_btn_save);
		oldPassword = (EditText) this.findViewById(R.id.change_passwrod_et_old);
		newPassword = (EditText) this.findViewById(R.id.change_passwrod_et_new);
		confirmPassword = (EditText) this
				.findViewById(R.id.change_passwrod_et_confirm);

		back.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		http = RequestHttp.getInstance(context);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_password_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		case R.id.change_password_btn_save:
			submit();
			break;

		default:
			break;
		}
	}

	private void submit() {
		sOld = oldPassword.getText().toString();
		sNew = newPassword.getText().toString();
		sConfirm = confirmPassword.getText().toString();
		if (sOld.equals("") || sNew.equals("") || sConfirm.equals("")) {
			Constants.showToast(context, "信息不能为空！");
		} else if (!sNew.equals(sConfirm)) {
			Constants.showToast(context, "两次输入的密码不一致！");
		} else if (sOld.equals(sConfirm)) {
			Constants.showToast(context, "新密码不能与旧密码相同");
		} else {
			CheckPasswordHandler mHandler = new CheckPasswordHandler();
			http.requestCheckPassword(mHandler, cusInfo.getId(), sOld);
			showDialog();
		}
	}

	private class CheckPasswordHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.obj.toString().equals("\"true\"")) {
				ChangePasswordHandler mHandler = new ChangePasswordHandler();
				http.requestChangePassword(mHandler, cusInfo.getId(), sConfirm);
			} else {
				waitDialog.dismiss();
				Constants.showToast(context, "原密码不正确，请确认后重试！");
			}
		}
	}

	private class ChangePasswordHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:

				ReLoginHandler reLoginHandler = new ReLoginHandler();
				http.requestLogin(reLoginHandler, cusInfo.getHandsetNo(),
						sConfirm);
				break;

			case RequestHttp.REQUEST_FAILER:
				waitDialog.dismiss();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				break;

			default:
				break;
			}
		}
	}

	private class ReLoginHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				waitDialog.dismiss();
				cusInfo = (CustomerInfo) msg.obj;
				application.setCustomerInfo(cusInfo);
				Constants.showToast(context, "密码修改成功！");
				ChangePasswordActivity.this.finish();
				break;

			case RequestHttp.REQUEST_FAILER:
				waitDialog.dismiss();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						"login");
				break;

			default:
				break;
			}
		}

	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(ChangePasswordActivity.this,
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
