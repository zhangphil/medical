package com.wemo.medical.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.wemo.medical.R;

public class SurveyTipsDialog extends Dialog implements OnClickListener {

	private TextView confirm;
	private TextView cancel;

	public SurveyTipsDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_survey_tips);

		initView();
	}

	private void initView() {

		confirm = (TextView) this.findViewById(R.id.dialog_survey_tips_confirm);
		cancel = (TextView) this.findViewById(R.id.dialog_survey_tips_cancel);

		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_survey_tips_confirm:
			Message msg = SurveyActivity.finishHandler.obtainMessage();
			msg.what = 200;
			msg.sendToTarget();
			break;

		case R.id.dialog_survey_tips_cancel:
			dismiss();
			break;

		default:
			break;
		}
	}
}
