package com.wemo.medical.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.History;

public class HistoryAdapter extends BaseAdapter {

	private List<History> histories;
	private LayoutInflater inflater;
	private int[] steps;
	private int[] stepsTwo;
	private final String TAG = "HistoryAdapter";

	public HistoryAdapter(List<History> histories, Context context,
			int[] steps, int[] setpsTwo) {
		this.histories = histories;
		inflater = LayoutInflater.from(context);
		this.steps = steps;
		this.stepsTwo = setpsTwo;
	}

	@Override
	public int getCount() {
		// if(histories.size()>9){
		// return 9;
		// }
		// else {
		return histories.size();
		// }
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
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
			convertView = inflater.inflate(R.layout.item_history, null);
			holder.date = (TextView) convertView
					.findViewById(R.id.item_history_tv_time);
			holder.year = (TextView) convertView
					.findViewById(R.id.item_history_tv_time_year);
			holder.events = (TextView) convertView
					.findViewById(R.id.item_history_tv_events);
			holder.status = (TextView) convertView
					.findViewById(R.id.item_history_tv_status);
			holder.step = (ImageView) convertView
					.findViewById(R.id.item_history_img_step);
			holder.setpLeft = (ImageView) convertView
					.findViewById(R.id.item_history_img_step_left);
			holder.setpRight = (ImageView) convertView
					.findViewById(R.id.item_history_img_step_right);
			holder.stepFl = (FrameLayout) convertView
					.findViewById(R.id.item_history_img_step_fl);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		History history = histories.get(position);
		holder.date.setText(history.getDate().substring(5, 10));
		holder.year.setText(history.getDate().substring(0, 4));
		holder.events.setText(history.getEvents());
		if (position < 9) {
			holder.stepFl.setVisibility(View.INVISIBLE);
			holder.step.setVisibility(View.VISIBLE);
			holder.step.setImageResource(steps[position]);
		} else {
			holder.stepFl.setVisibility(View.VISIBLE);
			holder.step.setVisibility(View.INVISIBLE);
			if (position % 10 == 9) {
				holder.setpRight.setImageResource(stepsTwo[0]);
				holder.setpLeft.setImageResource(stepsTwo[position / 10 + 1]);
			} else {
				holder.setpRight.setImageResource(stepsTwo[position % 10 + 1]);
				holder.setpLeft.setImageResource(stepsTwo[position / 10]);
			}

			if (history.getStatus().equals("0")) {

				holder.stepFl
						.setBackgroundResource(R.drawable.mydoctor_box_number_red);
			} else if (history.getStatus().equals("1")) {

				holder.stepFl
						.setBackgroundResource(R.drawable.mydoctor_box_number_green);
			} else {

				holder.stepFl
						.setBackgroundResource(R.drawable.mydoctor_box_number_orange);
			}
		}

		Log.d(TAG, history.getStatus());
		if (history.getStatus().equals("0")) {
			holder.events.setTextColor(Color.rgb(232, 115, 98));
			holder.status.setText("已拒绝");
			holder.status.setTextColor(Color.rgb(232, 115, 98));
			holder.step
					.setBackgroundResource(R.drawable.mydoctor_box_number_red);
		} else if (history.getStatus().equals("1")) {
			holder.events.setTextColor(Color.rgb(51, 204, 102));
			holder.status.setText("已接受");
			holder.status.setTextColor(Color.rgb(139, 137, 137));
			holder.step
					.setBackgroundResource(R.drawable.mydoctor_box_number_green);
		} else {
			holder.events.setTextColor(Color.rgb(255, 103, 39));
			holder.status.setText("待处理");
			holder.status.setTextColor(Color.rgb(255, 103, 39));
			holder.step
					.setBackgroundResource(R.drawable.mydoctor_box_number_orange);
		}
		return convertView;
	}

	class ViewHolder {
		TextView date;
		TextView year;
		TextView events;
		TextView status;
		ImageView step;
		ImageView setpLeft;
		ImageView setpRight;
		FrameLayout stepFl;
	}
}
