package com.wemo.medical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.RiskReport;

public class RiskReportAdapter extends BaseAdapter {

	private List<RiskReport> riskReports;
	private LayoutInflater inflater;

	public RiskReportAdapter(List<RiskReport> riskReports, Context context) {
		this.riskReports = riskReports;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return riskReports.size();
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
			convertView = inflater.inflate(R.layout.item_risk_report, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_risk_report_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.item_risk_report_time);
			holder.phone = (TextView) convertView
					.findViewById(R.id.item_risk_report_phone);
			holder.sex = (TextView) convertView
					.findViewById(R.id.item_risk_report_sex);
			holder.num = (TextView) convertView
					.findViewById(R.id.item_risk_report_num);
			holder.img = (ImageView) convertView
					.findViewById(R.id.item_risk_report_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		RiskReport riskReport = riskReports.get(position);
		holder.time.setText(riskReport.getAskDate());
		holder.name.setText("姓名：" + riskReport.getCusName());
		holder.phone.setText("电话：" + riskReport.getCusHandsetNo());
		holder.sex.setText("性别：" + riskReport.getSex());
		holder.num.setText("编号：" + riskReport.getAskId());

		return convertView;
	}

	class ViewHolder {
		TextView time;
		TextView name;
		TextView phone;
		TextView sex;
		TextView num;
		ImageView img;
	}

}
