package com.wemo.medical.activity;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.wemo.medical.R;

public class DoctorDialog extends Dialog {

	private TextView name;
	private TextView description;
	private Context context;
	private String doctorName;
	private String doctorDescription;

	public DoctorDialog(Context context, String doctorName,
			String doctorDescription) {
		super(context, R.style.dialog);

		setContentView(R.layout.doalog_doctor);

		this.context = context;
		this.doctorName = doctorName;
		this.doctorDescription = doctorDescription;

		initView();
	}

	private void initView() {
		name = (TextView) this.findViewById(R.id.dialog_doctor_name);
		description = (TextView) this
				.findViewById(R.id.dialog_doctor_description);

		name.setText(doctorName);
		description.setText("简介：" + doctorDescription);
	}
}
