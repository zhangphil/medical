package com.wemo.medical.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import com.wemo.medical.R;

/**
 * @see 出生日期选择的dialog
 * @author baiqiao
 * 
 */
@SuppressLint("NewApi")
public class DatePickerDialog extends Dialog implements
		android.view.View.OnClickListener, OnDateChangedListener {

	private DatePicker picker;
	private TextView confirm;
	private String birth;
	private int whichActivity;

	/**
	 * 
	 * @param context
	 * @param year
	 *            默认年
	 * @param month
	 *            默认月
	 * @param day
	 *            默认日
	 * @param whichActivity
	 *            从哪个界面弹出dialog 如果是“我的健管师”界面，则whichActivity为1
	 *            如果是“个人信息管理”界面弹出，则为2
	 */
	public DatePickerDialog(Context context, int year, int month, int day,
			int whichActivity) {
		super(context, R.style.dialog);

		setContentView(R.layout.dialog_birth_choice);

		birth = year + "-" + month + "-" + day;
		picker = (DatePicker) this.findViewById(R.id.dialog_birth_datepicker);
		confirm = (TextView) this.findViewById(R.id.dialog_birth_confirm);
		picker.init(year, month - 1, day, this);
		picker.setCalendarViewShown(false);
		this.whichActivity = whichActivity;
		if (whichActivity == 1) {
			picker.setMinDate(new Date().getTime());
		}
		if (whichActivity == 2) {
			picker.setMaxDate(new Date().getTime());
		}
		confirm.setOnClickListener(this);
	}

	/**
	 * 点击确认按钮发送选择的日期到个人信息管理界面
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.dialog_birth_confirm) {
			Message msg = null;
			if (whichActivity == 2) {
				msg = PersonalManageActivity.getBirthHandler.obtainMessage();
			} else {
				msg = DoctorActivity.getreservedDateHandler.obtainMessage();
			}

			msg.what = 200;
			msg.obj = birth;
			msg.sendToTarget();
			dismiss();
		}
	}

	/**
	 * 获取选择的出生日期
	 */
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(picker.getYear(), picker.getMonth(),
				picker.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		birth = sdf.format(calendar.getTime());
	}
}
