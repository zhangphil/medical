package com.wemo.medical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.PlanRemainTimes;

public class PlanRemainTimesAdapter extends BaseAdapter {

	private List<PlanRemainTimes> times;
	private LayoutInflater inflater;

	public PlanRemainTimesAdapter(Context context, List<PlanRemainTimes> times) {
		inflater = LayoutInflater.from(context);
		this.times = times;
	}

	@Override
	public int getCount() {
		return times.size();
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

		ViewHolder hodler = null;
		if (convertView == null) {
			hodler = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_plan_remian_times,
					null);
			hodler.times = (TextView) convertView
					.findViewById(R.id.item_plan_remain_times_times);
			hodler.type = (TextView) convertView
					.findViewById(R.id.item_plan_remain_times_name);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHolder) convertView.getTag();
		}

		PlanRemainTimes time = times.get(position);
		hodler.times.setText(time.getTimes() + "次");
		hodler.type.setText(time.getType() + "剩余次数：");
		return convertView;
	}

	class ViewHolder {
		TextView type;
		TextView times;
	}
}
