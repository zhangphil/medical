package com.wemo.medical.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wemo.medical.R;
import com.wemo.medical.adapter.PlanAdapter;
import com.wemo.medical.adapter.PlanRemainTimesAdapter;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Plan;
import com.wemo.medical.entity.PlanRemainTimes;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

@SuppressLint("HandlerLeak")
public class PlanActivity extends Activity implements OnClickListener {

	private Context context;
	private ImageView back;
	private List<Plan> plans;
	private List<PlanRemainTimes> times;
	private ListView mListView;
	private PlanAdapter planAdapter;
	private ImageView more;
	private PopupWindow popupWindow;
	private LayoutInflater inflater;
	private RequestHttp http;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private RelativeLayout layout;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_plan);

		initView();
		initMembers();
		showDialog();
	}

	private void initView() {
		context = PlanActivity.this;
		back = (ImageView) this.findViewById(R.id.plan_img_back);
		more = (ImageView) this.findViewById(R.id.plan_img_more);
		mListView = (ListView) this.findViewById(R.id.plan_listview);

		back.setOnClickListener(this);
		more.setOnClickListener(this);
	}

	private void initMembers() {
		inflater = LayoutInflater.from(context);
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		http = RequestHttp.getInstance(context);
		GetPlanHandler handler = new GetPlanHandler();
		http.requestGetPlan(handler, cusInfo.getId());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.plan_img_back:
			this.finish();
			ActivityStartAnim.TopToBottom(this);
			break;

		case R.id.plan_img_more:
			more.getTop();
			int y = more.getBottom() * 3 / 2;
			int x = getWindowManager().getDefaultDisplay().getWidth() / 4;
			showPopupWindow(x, y);
			break;

		default:
			break;
		}
	}

	/***
	 * 获取PopupWindow实例
	 */
	public void showPopupWindow(int x, int y) {
		layout = (RelativeLayout) LayoutInflater.from(PlanActivity.this)
				.inflate(R.layout.dialog_more, null);
		listView = (ListView) layout.findViewById(R.id.dialog_more_listview);
		PlanRemainTimesAdapter timeAdapter = new PlanRemainTimesAdapter(
				context, times);
		listView.setAdapter(timeAdapter);

		popupWindow = new PopupWindow(layout, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		if (times.size() == 0) {
			Constants.showToast(context, "没有剩余次数");
		} else {
			popupWindow.showAtLocation(findViewById(R.id.plan_img_more),
					Gravity.TOP, x, y);// 需要指定Gravity，默认情况是center.
		}
	}

	private void setListView() {
		planAdapter = new PlanAdapter(plans, context);
		mListView.setAdapter(planAdapter);
		mListView.setDividerHeight(0);
	}

	private class GetPlanHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				plans = (List<Plan>) ((List<Object>) msg.obj).get(0);
				times = (List<PlanRemainTimes>) ((List<Object>) msg.obj).get(1);
				if (plans.size() == 0) {
					Constants.showToast(context, "没有您的健管计划");
				}
				setListView();
				waitDialog.dismiss();
				break;

			case RequestHttp.REQUEST_FAILER:
				waitDialog.dismiss();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				break;

			default:
				break;
			}
		}
	}

	private void showDialog() {
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(PlanActivity.this, view);
		waitDialog.show();
		Object obj = iv_loading.getBackground();
		anim = (AnimationDrawable) obj;
		anim.stop();
		anim.start();
	}

	private View view;
	private ImageView iv_loading;
	private Dialog waitDialog;
	private AnimationDrawable anim = null;
}
