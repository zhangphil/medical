package com.wemo.medical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.Plan;

public class PlanAdapter extends BaseAdapter {

	private List<Plan> plans;
	private LayoutInflater inflater;

	public PlanAdapter(List<Plan> plans, Context context) {
		this.plans = plans;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return plans.size();
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
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_plan, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_plan_name);
			holder.content = (TextView) convertView
					.findViewById(R.id.item_plan_tv_task_content);
			holder.date = (TextView) convertView
					.findViewById(R.id.item_plan_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Plan plan = plans.get(position);
		holder.content.setText(plan.getContent());
		holder.name.setText("“" + plan.getName() + "”");
		holder.date.setText("任务开始日期： " + plan.getDate());
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView content;
		TextView date;
	}
}
