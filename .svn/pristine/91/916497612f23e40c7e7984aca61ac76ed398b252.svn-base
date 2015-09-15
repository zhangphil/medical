package com.wemo.medical.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wemo.medical.R;
import com.wemo.medical.adapter.RiskReportAdapter;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.RiskReport;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.view.CommentListView;
import com.wemo.medical.view.CommentListView.IXListViewListener;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class RiskActivity extends Activity implements OnClickListener,
		IXListViewListener, OnItemClickListener {

	private ImageView back;
	private Context context;
	private CommentListView listView;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private RequestHttp http;
	private List<RiskReport> riskReports;
	private List<RiskReport> riskReportsAll;
	private RiskReportAdapter riskReportAdapter;
	private RefreshHandler refreshHandler;
	private LoadMoreHandler loadMoreHandler;
	private int currentPage = 1;
	private int pageSize = 10;
	private Handler mHandler;
	private TextView button_ok;
	private TextView button_cancle;
	private List<RiskReport> surveyLists;
	private List<RiskReport> surveyListAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_risk);

		initView();
		initMembers();

		
	}

	private void initView() {
		context = RiskActivity.this;
		back = (ImageView) this.findViewById(R.id.risk_img_back);
		listView = (CommentListView) this.findViewById(R.id.risk_listview);

		back.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		riskReports = new ArrayList<RiskReport>();
		riskReportsAll = new ArrayList<RiskReport>();
		mHandler = new Handler();
		GetRiskReportHandler mHandler = new GetRiskReportHandler();
		http = RequestHttp.getInstance(context);
		refreshHandler = new RefreshHandler();
		loadMoreHandler = new LoadMoreHandler();
		http.requestGetRiskReport(mHandler, cusInfo.getId(), pageSize + "",
				currentPage + "");

		// 获取健康问卷列表
		surveyLists = new ArrayList<RiskReport>();
		surveyListAll = new ArrayList<RiskReport>();
		GetSurveyListHandler mSurHandler = new GetSurveyListHandler();
		http.requestGetSurveyList(mSurHandler, cusInfo.getId(), currentPage
				+ "");

		riskReportAdapter = new RiskReportAdapter(riskReportsAll, context);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(false);
		listView.setDividerHeight(0);
		listView.setAdapter(riskReportAdapter);
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(this);
		showDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.risk_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取健康问卷
	 */
	private class GetSurveyListHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				surveyLists = (List<RiskReport>) msg.obj;
				if (surveyLists.size() == 10) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				for (int i = 0; i < surveyLists.size(); i++) {
					surveyListAll.add(surveyLists.get(i));
				}

				Log.v("SureList", "" + surveyListAll.size());

				// 判断健康问卷是否有填写
				isListNull(surveyListAll);

				// 判断评估风险报告与健康方式问卷是否为空
		        noRisk();
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

	private class GetRiskReportHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				riskReports = (List<RiskReport>) msg.obj;
				for (int i = 0; i < riskReports.size(); i++) {
					riskReportsAll.add(riskReports.get(i));

				} // 判断是否有评估风险报告
				isListNull(riskReportsAll);

				if (riskReports.size() == pageSize) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				riskReportAdapter.notifyDataSetChanged();
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

	private class RefreshHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				riskReports = (List<RiskReport>) msg.obj;
				riskReportsAll.clear();
				for (int i = 0; i < riskReports.size(); i++) {
					riskReportsAll.add(riskReports.get(i));
				}
				if (riskReports.size() == pageSize) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				riskReportAdapter.notifyDataSetChanged();
				stopRefresh();

				break;

			case RequestHttp.REQUEST_FAILER:
				stopRefresh();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				break;

			default:
				break;
			}
		}
	}

	private class LoadMoreHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				riskReports = (List<RiskReport>) msg.obj;
				for (int i = 0; i < riskReports.size(); i++) {
					riskReportsAll.add(riskReports.get(i));
				}
				if (riskReports.size() == pageSize) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				riskReportAdapter.notifyDataSetChanged();
				stopLoadMore();
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage = 1;
				RefreshHandler mHandler = new RefreshHandler();
				http.requestGetRiskReport(mHandler, cusInfo.getId(),
						pageSize + "", currentPage + "");

			}
		}, 500);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage++;
				GetRiskReportHandler mHandler = new GetRiskReportHandler();
				http.requestGetRiskReport(mHandler, cusInfo.getId(),
						pageSize + "", currentPage + "");
			}
		}, 500);
	}

	private void stopRefresh() {
		listView.stopRefresh();
		String time = Constants.getStringDate();
		listView.setRefreshTime(time);
	}

	private void stopLoadMore() {
		listView.stopLoadMore();
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(RiskActivity.this, view);
		waitDialog.show();
		Object obj = iv_loading.getBackground();
		anim = (AnimationDrawable) obj;
		anim.stop();
		anim.start();

	}

	private View view;
	private LayoutInflater inflater;
	private ImageView iv_loading;
	private Dialog waitDialog;
	private AnimationDrawable anim = null;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(RiskActivity.this, RiskReportActivity.class);
		intent.putExtra("token", cusInfo.getToken());
		intent.putExtra("id", riskReports.get(arg2 - 1).getId());
		startActivity(intent);
		ActivityStartAnim.RightToLeft2(this);
	}

	/**
	 * 2015-3 判断list是否为空
	 * 
	 * @author lxh
	 */
	private boolean isListNull(List list) {
		if (list.size() == 0) {

			return true;
		}
		return false;
	}

	/**
	 * 2015-3 判断评估风险报告与健康方式问卷是否为空
	 * 
	 * @author lxh
	 */
	private void noRisk() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.v("Thread_start", ">>>>>>");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					public void run() {
						Log.v("Thread_sleep_over", ">>>>>>");

						if (isListNull(riskReportsAll)) {
							if (isListNull(surveyListAll)) {
								LayoutInflater inflater = LayoutInflater
										.from(context);
								View view = inflater.inflate(
										R.layout.dialog_no_risk_report, null);
								final AlertDialog alertDialog = new AlertDialog.Builder(
										context).create();
								alertDialog.setView(view);
								button_ok = (TextView) view
										.findViewById(R.id.dialog_no_risk_tips_cancel);
								button_cancle = (TextView) view
										.findViewById(R.id.dialog_no_risk_tips_confirm);
								button_ok
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent(
														RiskActivity.this,
														SurveyActivity.class);
												startActivity(intent);
												ActivityStartAnim
														.BottomToTop(RiskActivity.this);
												alertDialog.dismiss();
												finish();

											}
										});
								button_cancle
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												alertDialog.dismiss();
												finish();
											}
										});
								alertDialog.show();

							} else {

								Toast.makeText(context, "您的评估报告尚未出来，请耐心等待。",
										Toast.LENGTH_LONG).show();

							}
						}

					}
				});

			}
		}).start();
	}
}
