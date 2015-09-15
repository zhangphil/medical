package com.wemo.medical.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.activity.DoctorActivity;
import com.wemo.medical.activity.DoctorLectureActivity;
import com.wemo.medical.activity.DoctorMonitorActivity;
import com.wemo.medical.activity.ExamActivity;
import com.wemo.medical.activity.ManagementActivity;
import com.wemo.medical.activity.MedicalApplication;
import com.wemo.medical.activity.PlanActivity;
import com.wemo.medical.activity.RiskActivity;
import com.wemo.medical.activity.SurveyListActivity;
import com.wemo.medical.db.SqliteHelper;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;

public class MenuFragment extends Fragment implements OnClickListener {

	private View view;
	private Activity mActivity;
	private View mParent;
	private TextView plan;
	private TextView doctor;
	private TextView doctor_monitor;
	private TextView doctor_lecture;
	private ImageView management;
	private TextView survey;
	private TextView risk;
	private TextView examination;
	private MedicalApplication application;
	private CustomerInfo customerInfo;
	private View view_plan;

	private Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.menu_scrollview, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initMembers();
		// 判断客户类型
		cUstype();
	}

	@Override
	public void onClick(View v) {
		// ((MainActivity)getActivity()).mMenuDrawer.setActiveView(v);
		// ((MainActivity)getActivity()).mMenuDrawer.closeMenu();
		// ((MainActivity)getActivity()).mActiveViewId = v.getId();
		switch (v.getId()) {
		case R.id.left_plan:
			intent = new Intent(mActivity, PlanActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			break;

		case R.id.left_doctor:
			if (customerInfo.getHealMngId() == null) {
				Constants.showToast(mActivity, "您尚未分配健管师，请联系管理员");
			} else {
				intent = new Intent(mActivity, DoctorActivity.class);
				startActivity(intent);
				ActivityStartAnim.BottomToTop(mActivity);
			}

			break;
		case R.id.left_monitor:
			intent=new Intent(mActivity,DoctorMonitorActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			
			break;
		case R.id.left_lecture:
			intent=new Intent(mActivity,DoctorLectureActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			break;
		case R.id.left_setting:
			intent = new Intent(mActivity, ManagementActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			break;

		case R.id.left_survey:
			intent = new Intent(mActivity, SurveyListActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			break;

		case R.id.left_risk_report:
			// Message msg = MainActivity.finishHandler.obtainMessage();
			// msg.what = 201;
			// msg.sendToTarget();
			// break;
			intent = new Intent(mActivity, RiskActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			break;

		case R.id.left_examination_report:
			intent = new Intent(mActivity, ExamActivity.class);
			startActivity(intent);
			ActivityStartAnim.BottomToTop(mActivity);
			break;

		default:
			break;
		}
	}

	private void initView() {
		mActivity = getActivity();
		mParent = getView();
		plan = (TextView) mParent.findViewById(R.id.left_plan);
		doctor = (TextView) mParent.findViewById(R.id.left_doctor);
		doctor_monitor=(TextView)mParent.findViewById(R.id.left_monitor);
		doctor_lecture=(TextView)mParent.findViewById(R.id.left_lecture);
		management = (ImageView) mParent.findViewById(R.id.left_setting);
		survey = (TextView) mParent.findViewById(R.id.left_survey);
		risk = (TextView) mParent.findViewById(R.id.left_risk_report);
		examination = (TextView) mParent
				.findViewById(R.id.left_examination_report);
		view_plan = mParent.findViewById(R.id.left_line_plan);
		plan.setOnClickListener(this);
		doctor.setOnClickListener(this);
		/*----------下句为doctor_monitor添加点击监听事件------------*/
		//doctor_monitor.setOnClickListener(this);
		doctor_lecture.setOnClickListener(this);
		management.setOnClickListener(this);
		survey.setOnClickListener(this);
		risk.setOnClickListener(this);
		examination.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) mActivity.getApplication();
		customerInfo = application.getCustomerInfo();
	}

	/**
	 * 2015-3 根据CusType判断用户类型
	 * @author lxh 
	 * 
	 */
	private void cUstype() {
		SqliteHelper sqliteHelper = new SqliteHelper(getActivity());
		String mCusType = sqliteHelper.getUser().getCusTypeId();

		Log.v("CusType", mCusType);
		if (mCusType.equals("198")) {
			plan.setVisibility(View.GONE);
			view_plan.setVisibility(View.GONE);
		}

	}

}
