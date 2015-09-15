package com.wemo.medical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wemo.medical.R;
import com.wemo.medical.view.DrawView;

public class HealthyRiskActivity extends Activity implements OnClickListener {

	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_healthy_risk);

		initView();
		initDraw();
	}

	private void initView() {
		back = (ImageView) this.findViewById(R.id.healthy_risk_img_back);

		back.setOnClickListener(this);
	}

	private void initDraw() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.healthy_risk_draw);
		final DrawView view = new DrawView(this);
		view.setMinimumHeight(android.view.ViewGroup.LayoutParams.FILL_PARENT);
		view.setMinimumWidth(android.view.ViewGroup.LayoutParams.FILL_PARENT);
		// 通知view组件重绘
		view.invalidate();
		layout.addView(view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.healthy_risk_img_back:
			this.finish();
			break;

		default:
			break;
		}
	}

}
