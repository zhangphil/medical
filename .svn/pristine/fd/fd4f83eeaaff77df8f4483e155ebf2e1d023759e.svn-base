package com.wemo.medical.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.Exam;

@SuppressLint("InflateParams")
public class ExamAdapter extends BaseAdapter {

	private List<Exam> exams;
	private LayoutInflater inflater;

	public ExamAdapter(List<Exam> exams, Context context) {
		this.exams = exams;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return exams.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_exam, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_exam_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.item_exam_time);
			holder.phone = (TextView) convertView
					.findViewById(R.id.item_exam_phone);
			holder.sex = (TextView) convertView
					.findViewById(R.id.item_exam_sex);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Exam exam = exams.get(position);
		holder.time.setText(exam.getDate());
		holder.name.setText("姓名: 	  " + exam.getName());
		holder.phone.setText("体检号: " + exam.getExamNo());
		holder.sex.setText("性别: 	  " + exam.getSex());
		return convertView;
	}

	class ViewHolder {
		TextView time;
		TextView name;
		TextView phone;
		TextView sex;
	}
}
