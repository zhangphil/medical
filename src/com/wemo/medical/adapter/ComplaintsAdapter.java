package com.wemo.medical.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.Complaints;

public class ComplaintsAdapter extends BaseAdapter {

	private List<Complaints> complaints;
	private Context context;
	private LayoutInflater inflater;

	public ComplaintsAdapter(List<Complaints> complaints, Context context) {
		this.complaints = complaints;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return complaints.size();
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
			convertView = inflater.inflate(R.layout.item_complaints, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_complaints_name);
			holder.status = (TextView) convertView
					.findViewById(R.id.item_complaints_status);
			holder.step = (ImageView) convertView
					.findViewById(R.id.item_complaints_step);
			holder.stepPoint = (ImageView) convertView
					.findViewById(R.id.item_complaints_step_point);
			holder.date = (TextView) convertView
					.findViewById(R.id.item_complaints_date);
			holder.reason = (TextView) convertView
					.findViewById(R.id.item_complaints_reason);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Complaints complaint = complaints.get(position);
		if (complaint.getStatus().equals("1")) {
			holder.name.setText(complaint.getName());
			holder.status.setText("处理中");
			holder.status.setTextColor(Color.rgb(255, 103, 39));
			holder.step.setImageResource(R.drawable.set_line_orange);
			holder.stepPoint.setImageResource(R.drawable.set_icon_orange);
			holder.date.setText(complaint.getDate().substring(0, 10));

		} else {
			holder.name.setText(complaint.getName());
			holder.status.setText("已处理");
			holder.status.setTextColor(Color.rgb(51, 204, 102));
			holder.step.setImageResource(R.drawable.set_line_green);
			holder.stepPoint.setImageResource(R.drawable.set_icon_green);
			holder.date.setText(complaint.getDate().substring(0, 10));
		}
		holder.reason.setText("投诉原因： " + complaint.getReason());
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView status;
		ImageView step;
		ImageView stepPoint;
		TextView date;
		TextView reason;
	}
}
