package com.wemo.medical.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wemo.medical.R;

public class ConfirmExitDialog extends Dialog implements
		android.view.View.OnClickListener {

	private TextView cancel;
	private TextView confirm;

	public ConfirmExitDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_confirm_exit);
		initView();
	}

	private void initView() {
		cancel = (TextView) this.findViewById(R.id.dialog_confirm_exit_cancel);
		confirm = (TextView) this
				.findViewById(R.id.dialog_confirm_exit_confirm);

		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_confirm_exit_cancel:
			dismiss();
			break;

		case R.id.dialog_confirm_exit_confirm:
			Message msg = MainActivity.finishHandler.obtainMessage();
			msg.what = 200;
			msg.sendToTarget();
			dismiss();
			break;

		default:
			break;
		}
	}

}
