package com.wemo.medical.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wemo.medical.R;

public class DoctorTipsDialog extends Dialog implements OnClickListener {

	private TextView confirm;

	public DoctorTipsDialog(Context context) {
		super(context, R.style.dialog);

		setContentView(R.layout.dialog_doctor_tips);

		confirm = (TextView) this.findViewById(R.id.dialog_doctor_tv_confirm);

		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_doctor_tv_confirm:
			Message msg = DoctorActivity.getreservedDateHandler.obtainMessage();
			msg.what = 100;
			msg.sendToTarget();
			dismiss();
			break;

		default:
			break;
		}
	}

}
