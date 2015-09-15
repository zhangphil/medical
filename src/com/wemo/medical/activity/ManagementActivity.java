package com.wemo.medical.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.db.SqliteHelper;
import com.wemo.medical.util.ActivityStartAnim;

@SuppressLint("HandlerLeak")
public class ManagementActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private Context context;
	private ImageView back;
	private TextView personal;
	private TextView complaints;
	private TextView cancellation;
	private TextView changePassword;
	private LinearLayout submit_msg_layout_menu;
	private Intent intent;
	private Button btnCancellation;
	private Button cancel;
	public static Handler finishHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_management);

		initView();
		setHandler();
	}

	private void initView() {
		context = ManagementActivity.this;
		back = (ImageView) this.findViewById(R.id.management_img_back);
		personal = (TextView) this.findViewById(R.id.management_btn_personal);
		complaints = (TextView) this
				.findViewById(R.id.management_complaints_manage);
		cancellation = (TextView) this
				.findViewById(R.id.management_cancellation);
		changePassword = (TextView) this
				.findViewById(R.id.management_change_password);
		submit_msg_layout_menu = (LinearLayout) this
				.findViewById(R.id.management_layout_menu);
		btnCancellation = (Button) this
				.findViewById(R.id.management_btn_cancellation);
		cancel = (Button) this.findViewById(R.id.management_btn_cancel);

		back.setOnClickListener(this);
		personal.setOnClickListener(this);
		complaints.setOnClickListener(this);
		cancellation.setOnClickListener(this);
		changePassword.setOnClickListener(this);
		btnCancellation.setOnClickListener(this);
		cancel.setOnClickListener(this);

		back.setOnTouchListener(this);
		personal.setOnTouchListener(this);
		complaints.setOnTouchListener(this);
		changePassword.setOnTouchListener(this);
	}

	private void setHandler() {
		finishHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 200) {
					ManagementActivity.this.finish();
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.management_img_back:
			this.finish();
			ActivityStartAnim.TopToBottom(this);
			submit_msg_layout_menu.setVisibility(View.INVISIBLE);
			cancellation.setVisibility(View.VISIBLE);
			break;

		case R.id.management_btn_personal:
			intent = new Intent(ManagementActivity.this,
					PersonalManageActivity.class);
			startActivity(intent);
			ActivityStartAnim.RightToLeft2(this);
			submit_msg_layout_menu.setVisibility(View.INVISIBLE);
			cancellation.setVisibility(View.VISIBLE);
			break;

		case R.id.management_complaints_manage:
			intent = new Intent(ManagementActivity.this,
					ComplaintsActivity.class);
			startActivity(intent);
			ActivityStartAnim.RightToLeft2(this);
			submit_msg_layout_menu.setVisibility(View.INVISIBLE);
			cancellation.setVisibility(View.VISIBLE);
			break;

		case R.id.management_change_password:
			intent = new Intent(ManagementActivity.this,
					ChangePasswordActivity.class);
			startActivity(intent);
			ActivityStartAnim.RightToLeft2(this);
			submit_msg_layout_menu.setVisibility(View.INVISIBLE);
			cancellation.setVisibility(View.VISIBLE);
			break;

		case R.id.management_cancellation:

			submit_msg_layout_menu.setVisibility(View.VISIBLE);
			Animation anim = AnimationUtils.loadAnimation(this,
					R.anim.move_start);
			submit_msg_layout_menu.startAnimation(anim);
			cancellation.setVisibility(View.INVISIBLE);
			break;

		case R.id.management_btn_cancellation:
			intent = new Intent(ManagementActivity.this, LoginActivity.class);
			startActivity(intent);
			SqliteHelper helper = new SqliteHelper(context);
			helper.deleteCustomerInfo();
			Message msg = MainActivity.finishHandler.obtainMessage();
			msg.what = 200;
			msg.sendToTarget();
			this.finish();
			break;

		case R.id.management_btn_cancel:
			submit_msg_layout_menu.setVisibility(View.GONE);
			cancellation.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.management_img_back:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				submit_msg_layout_menu.setVisibility(View.INVISIBLE);
				cancellation.setVisibility(View.VISIBLE);
			}

			break;

		case R.id.management_btn_personal:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				submit_msg_layout_menu.setVisibility(View.INVISIBLE);
				cancellation.setVisibility(View.VISIBLE);
			}
			break;

		case R.id.management_complaints_manage:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				submit_msg_layout_menu.setVisibility(View.INVISIBLE);
				cancellation.setVisibility(View.VISIBLE);
			}
			break;

		case R.id.management_change_password:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				submit_msg_layout_menu.setVisibility(View.INVISIBLE);
				cancellation.setVisibility(View.VISIBLE);
			}
			break;

		default:
			break;
		}
		return false;
	}

}
