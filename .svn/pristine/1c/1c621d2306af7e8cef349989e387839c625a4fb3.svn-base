package com.wemo.medical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.RiskReport;

public class SurveyListAdapter extends BaseAdapter {

	private List<RiskReport> surveyLists;
	private LayoutInflater inflater;

	public SurveyListAdapter(List<RiskReport> riskReports, Context context) {
		this.surveyLists = riskReports;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return surveyLists.size();
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
			convertView = inflater.inflate(R.layout.item_survey_list, null);
			holder.num = (TextView) convertView
					.findViewById(R.id.item_survey_list_id);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_survey_list_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.item_survey_list_date);
			holder.sex = (TextView) convertView
					.findViewById(R.id.item_survey_list_sex);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		RiskReport surveyList = surveyLists.get(position);
		holder.num.setText("#" + surveyList.getAskId());
		holder.time.setText(surveyList.getAskDate().substring(0, 10));
		holder.name.setText("姓名：" + surveyList.getCusName());
		holder.sex.setText("性别：" + surveyList.getSex());

		return convertView;
	}

	class ViewHolder {
		TextView num;
		TextView time;
		TextView name;
		TextView sex;
	}

}
