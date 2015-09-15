package com.wemo.medical.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.wemo.medical.R;
import com.wemo.medical.adapter.ComplaintsAdapter;
import com.wemo.medical.entity.Complaints;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.view.CommentListView;
import com.wemo.medical.view.CommentListView.IXListViewListener;

@SuppressLint("HandlerLeak")
public class ComplaintsActivity extends Activity implements OnClickListener,
		IXListViewListener {

	private final String TAG = "ComplaintsActivity";
	private ImageView back;
	private ImageView edit;
	private Context context;
	private CommentListView mListView;
	private List<Complaints> complaints;
	private ComplaintsAdapter complaintsAdapter;
	private View header;
	private View footer;
	private LayoutInflater inflater;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private RequestHttp http;
	private GetComplaintsHandler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_complaints);

		initView();
		initMembers();
	}

	private void initView() {
		context = ComplaintsActivity.this;
		inflater = LayoutInflater.from(context);
		back = (ImageView) this.findViewById(R.id.complaints_img_back);
		edit = (ImageView) this.findViewById(R.id.complaints_img_edit);
		mListView = (CommentListView) this
				.findViewById(R.id.complaints_listview);
		header = inflater.inflate(R.layout.complaints_header, null);
		footer = inflater.inflate(R.layout.complaints_footer, null);
		mListView.addHeaderView(header, null, false);
		mListView.addFooterView(footer, null, false);
		mListView.setPullLoadEnable(false);
		mListView.setXListViewListener(this);
		back.setOnClickListener(this);
		edit.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		complaints = new ArrayList<Complaints>();
		cusInfo = application.getCustomerInfo();
		http = RequestHttp.getInstance(context);
		mHandler = new GetComplaintsHandler();

		http.requestGetComplaintList(mHandler, cusInfo.getId());
		showDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.complaints_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		case R.id.complaints_img_edit:
			ComplaintsDialog dialog = new ComplaintsDialog(context);
			dialog.show();
			break;

		default:
			break;
		}
	}

	private void setListView() {
		complaintsAdapter = new ComplaintsAdapter(complaints, context);
		mListView.setAdapter(complaintsAdapter);
		mListView.setDividerHeight(0);
	}

	private class GetComplaintsHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				complaints = (List<Complaints>) msg.obj;
				Log.d(TAG, complaints.size() + "");
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

	private class RefreshHandler extends Handler {
		@Override
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				complaints = (List<Complaints>) msg.obj;
				Log.d(TAG, complaints.size() + "");
				setListView();
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

	@Override
	public void onRefresh() {
		RefreshHandler refreshHandler = new RefreshHandler();
		http.requestGetComplaintList(refreshHandler, cusInfo.getId());
	}

	@Override
	public void onLoadMore() {

	}

	private void stopRefresh() {
		mListView.stopRefresh();
		String time = Constants.getStringDate();
		mListView.setRefreshTime(time);
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(ComplaintsActivity.this,
				view);
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
