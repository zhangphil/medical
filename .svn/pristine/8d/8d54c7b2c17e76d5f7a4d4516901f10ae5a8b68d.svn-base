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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.wemo.medical.R;
import com.wemo.medical.adapter.ExamAdapter;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Exam;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.view.CommentListView;
import com.wemo.medical.view.CommentListView.IXListViewListener;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class ExamActivity extends Activity implements OnClickListener,
		IXListViewListener, OnItemClickListener {

	private ImageView back;
	private Context context;
	private CommentListView listView;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private RequestHttp http;
	private int currentPage = 1;
	private List<Exam> exams;
	private List<Exam> examAll;
	private ExamAdapter examAdapter;
	private Handler mHandler;
	private GetExamHandler getExamHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exam);

		initView();
		initMembers();
	}

	private void initView() {
		context = ExamActivity.this;
		back = (ImageView) this.findViewById(R.id.exam_img_back);
		listView = (CommentListView) this.findViewById(R.id.exam_listview);

		back.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		cusInfo = application.getCustomerInfo();
		exams = new ArrayList<Exam>();
		examAll = new ArrayList<Exam>();
		mHandler = new Handler();
		getExamHandler = new GetExamHandler();
		http = RequestHttp.getInstance(context);
		http.requestGetExamList(getExamHandler, cusInfo.getId(), currentPage
				+ "");
		examAdapter = new ExamAdapter(examAll, context);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(false);
		listView.setDividerHeight(0);
		listView.setAdapter(examAdapter);
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(this);
		showDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exam_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		default:
			break;
		}
	}

	private class GetExamHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				exams = (List<Exam>) msg.obj;
				if (exams.size() == 10) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				for (int i = 0; i < exams.size(); i++) {
					examAll.add(exams.get(i));
				}
				examAdapter.notifyDataSetChanged();
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
				exams = (List<Exam>) msg.obj;
				examAll.clear();
				if (exams.size() == 10) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				for (int i = 0; i < exams.size(); i++) {
					examAll.add(exams.get(i));
				}
				examAdapter.notifyDataSetChanged();
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
				exams = (List<Exam>) msg.obj;
				if (exams.size() == 10) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				for (int i = 0; i < exams.size(); i++) {
					examAll.add(exams.get(i));
				}
				examAdapter.notifyDataSetChanged();
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
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage = 1;
				RefreshHandler refreshHandler = new RefreshHandler();
				http.requestGetExamList(refreshHandler, cusInfo.getId(),
						currentPage + "");

			}
		}, 500);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage++;
				LoadMoreHandler loadMoreHandler = new LoadMoreHandler();
				http.requestGetExamList(loadMoreHandler, cusInfo.getId(),
						currentPage + "");
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

		waitDialog = Constants.createLoadingDialog(ExamActivity.this, view);
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
		Intent intent = new Intent(ExamActivity.this, ExamDetailActivity.class);
		intent.putExtra("examId", examAll.get(arg2 - 1).getId());
		intent.putExtra("examNo", examAll.get(arg2 - 1).getExamNo());
		startActivity(intent);
		ActivityStartAnim.RightToLeft2(this);
	}
}
