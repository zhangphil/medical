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
import com.wemo.medical.adapter.HistoryAdapter;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.History;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.view.CommentListView;
import com.wemo.medical.view.CommentListView.IXListViewListener;

@SuppressLint("HandlerLeak")
public class HistoryActivity extends Activity implements OnClickListener,
		OnItemClickListener, IXListViewListener {

	private ImageView back;
	private List<History> histories;
	private List<History> historyAll;
	private CommentListView mListView;
	private HistoryAdapter historyAdapter;
	private Context context;
	private int[] steps;
	private int[] setpsTwo;
	private int currentPage = 1;
	private Intent intent;
	private MedicalApplication application;
	private CustomerInfo info;
	private RequestHttp http;
	private View header;
	private LayoutInflater inflater;
	private Handler mHandler;
	private LoadMoreHandler loadMoreHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history);

		initView();
		initMembers();
		setStep();
	}

	private void initView() {
		context = HistoryActivity.this;
		inflater = LayoutInflater.from(this);
		back = (ImageView) this.findViewById(R.id.history_img_back);
		mListView = (CommentListView) this.findViewById(R.id.history_listview);

		header = inflater.inflate(R.layout.history_header, null);
		// mListView.addHeaderView(header, null, false);

		back.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setPullRefreshEnable(false);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);

	}

	private void initMembers() {

		application = (MedicalApplication) getApplication();
		info = application.getCustomerInfo();
		mHandler = new Handler();
		historyAll = new ArrayList<History>();
		http = RequestHttp.getInstance(context);
		GetHistoryHandler mHandler = new GetHistoryHandler();
		loadMoreHandler = new LoadMoreHandler();
		http.requestGetHistory(mHandler, info.getId(), currentPage + "");
		// setListView();
		showDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.history_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置历史步骤的每一步的数字图片
	 */
	private void setStep() {

		steps = new int[] { R.drawable.number_one, R.drawable.number_two,
				R.drawable.number_three, R.drawable.number_four,
				R.drawable.number_five, R.drawable.number_six,
				R.drawable.number_seven, R.drawable.number_eight,
				R.drawable.number_nine };

		setpsTwo = new int[] { R.drawable.s0, R.drawable.s1, R.drawable.s2,
				R.drawable.s3, R.drawable.s4, R.drawable.s5, R.drawable.s6,
				R.drawable.s7, R.drawable.s8, R.drawable.s9 };
	}

	private void setListView() {
		historyAdapter = new HistoryAdapter(historyAll, context, steps,
				setpsTwo);
		mListView.setAdapter(historyAdapter);
		mListView.setDividerHeight(0);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		intent = new Intent(HistoryActivity.this, DoctorReplyActivity.class);
		intent.putExtra("historyId", historyAll.get(arg2 - 1).getId());
		startActivity(intent);
		ActivityStartAnim.RightToLeft2(this);
	}

	private class GetHistoryHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				histories = (List<History>) msg.obj;
				for (int i = 0; i < histories.size(); i++) {
					historyAll.add(histories.get(i));
				}
				if (histories.size() == 10) {
					mListView.setPullLoadEnable(true);
				} else {
					mListView.setPullLoadEnable(false);
				}
				// historyAdapter.notifyDataSetChanged();
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

	private class LoadMoreHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				histories = (List<History>) msg.obj;
				for (int i = 0; i < histories.size(); i++) {
					historyAll.add(histories.get(i));
				}
				if (histories.size() == 10) {
					mListView.setPullLoadEnable(true);
				} else {
					mListView.setPullLoadEnable(false);
				}
				historyAdapter.notifyDataSetChanged();
				mListView.stopLoadMore();
				break;

			case RequestHttp.REQUEST_FAILER:
				mListView.stopLoadMore();
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

		waitDialog = Constants.createLoadingDialog(HistoryActivity.this, view);
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

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				currentPage++;
				http.requestGetHistory(loadMoreHandler, info.getId(),
						currentPage + "");
			}
		}, 500);
	}

}
