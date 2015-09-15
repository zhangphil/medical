package com.wemo.medical.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.wemo.medical.R;
import com.wemo.medical.adapter.SurveyListAdapter;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.RiskReport;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.view.CommentListView;
import com.wemo.medical.view.CommentListView.IXListViewListener;

@SuppressLint("HandlerLeak")
public class SurveyListActivity extends Activity implements OnClickListener,
		IXListViewListener, OnItemClickListener {

	private ImageView back;
	private ImageView edit;
	private Intent intent;
	private CommentListView listView;
	private RequestHttp http;
	private MedicalApplication application;
	private CustomerInfo customerInfo;
	private Context context;
	private List<RiskReport> surveyLists;
	private List<RiskReport> surveyListAll;
	private SurveyListAdapter surveyListAdapter;
	private int currentPage = 1;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_survey_list);

		initView();
		initMembers();
		setListView();
	}

	private void initView() {
		context = SurveyListActivity.this;
		back = (ImageView) this.findViewById(R.id.survey_personal_info_back);
		edit = (ImageView) this.findViewById(R.id.survey_list_edit);
		listView = (CommentListView) this
				.findViewById(R.id.survey_list_listview);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(true);

		back.setOnClickListener(this);
		edit.setOnClickListener(this);
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		customerInfo = application.getCustomerInfo();
		handler = new Handler();
		surveyLists = new ArrayList<RiskReport>();
		surveyListAll = new ArrayList<RiskReport>();
		http = RequestHttp.getInstance(context);
		GetSurveyListHandler mHandler = new GetSurveyListHandler();
		http.requestGetSurveyList(mHandler, customerInfo.getId(), currentPage
				+ "");
		showDialog();
	}

	private void setListView() {
		surveyListAdapter = new SurveyListAdapter(surveyListAll, context);
		listView.setAdapter(surveyListAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.survey_personal_info_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		case R.id.survey_list_edit:
			intent = new Intent(SurveyListActivity.this, SurveyActivity.class);
			startActivity(intent);
			ActivityStartAnim.RightToLeft2(this);

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		intent = new Intent(SurveyListActivity.this, SurveyDetailActivty.class);
		intent.putExtra("id", surveyListAll.get(arg2 - 1).getId());
		startActivity(intent);
		ActivityStartAnim.BottomToTop(this);
	}

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
				surveyListAdapter.notifyDataSetChanged();
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
				surveyLists = (List<RiskReport>) msg.obj;
				surveyListAll.clear();
				if (surveyLists.size() == 10) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				for (int i = 0; i < surveyLists.size(); i++) {
					surveyListAll.add(surveyLists.get(i));
				}
				surveyListAdapter.notifyDataSetChanged();
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
				surveyLists = (List<RiskReport>) msg.obj;
				if (surveyLists.size() == 10) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				for (int i = 0; i < surveyLists.size(); i++) {
					surveyListAll.add(surveyLists.get(i));
				}
				surveyListAdapter.notifyDataSetChanged();
				stopLoadMore();
				break;

			case RequestHttp.REQUEST_FAILER:
				stopLoadMore();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				break;

			default:
				break;
			}

		}
	}

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage = 1;
				RefreshHandler refreshHandler = new RefreshHandler();
				http.requestGetSurveyList(refreshHandler, customerInfo.getId(),
						currentPage + "");
			}
		}, 500);
	}

	@Override
	public void onLoadMore() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage++;
				LoadMoreHandler loadMoreHandler = new LoadMoreHandler();
				http.requestGetSurveyList(loadMoreHandler,
						customerInfo.getId(), currentPage + "");
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

		waitDialog = Constants.createLoadingDialog(SurveyListActivity.this,
				view);
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

}
