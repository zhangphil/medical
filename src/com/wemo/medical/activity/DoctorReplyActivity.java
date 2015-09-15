package com.wemo.medical.activity;

import net.tsz.afinal.FinalBitmap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.entity.Doctor;
import com.wemo.medical.entity.HistoryDetail;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.widget.CircularImage;

@SuppressLint("HandlerLeak")
public class DoctorReplyActivity extends Activity implements OnClickListener {

	private Context context;
	private CircularImage head;
	private MedicalApplication application;
	private ImageView back;
	private String historyId;
	private RequestHttp http;
	private HistoryDetail detail;
	private TextView doctorName;
	private TextView reqStatus;
	private TextView reqType;
	private TextView reqTime;
	private TextView reqContent;
	private TextView doctorReply;
	private TextView doctorReply2;
	private TextView doctorDes;
	private Doctor doctor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_doctor_reply);

		initView();
		initMembers();
		initData();
	}

	private void initView() {
		context = DoctorReplyActivity.this;
		head = (CircularImage) this.findViewById(R.id.doctor_reply_img_head);
		back = (ImageView) this.findViewById(R.id.doctor_reply_img_back);
		doctorName = (TextView) this.findViewById(R.id.doctor_reply_tv_name);
		doctorDes = (TextView) this.findViewById(R.id.doctor_reply_tv_detail);
		doctorReply = (TextView) this
				.findViewById(R.id.doctor_reply_tv_doctor_reply);
		doctorReply2 = (TextView) this.findViewById(R.id.tv_doctor_reply);
		reqStatus = (TextView) this
				.findViewById(R.id.doctor_reply_tv_request_status);
		reqType = (TextView) this
				.findViewById(R.id.doctor_reply_tv_request_type);
		reqTime = (TextView) this
				.findViewById(R.id.doctor_reply_tv_reserve_time);
		reqContent = (TextView) this
				.findViewById(R.id.doctor_reply_tv_request_content);
		head.setImageResource(R.drawable.set_change_userdata_icon);

		back.setOnClickListener(this);
		doctorDes.setOnClickListener(this);
	}

	private void initMembers() {
		application = (MedicalApplication) getApplication();
		historyId = getIntent().getStringExtra("historyId");
		http = RequestHttp.getInstance(context);
		GetHistoryDetailHandler mHandler = new GetHistoryDetailHandler();
		http.requestGetHistoryDetail(mHandler, historyId);
		showDialog();

	}

	private void initData() {
		doctor = application.getDoctor();
		FinalBitmap fb = FinalBitmap.create(context);
		String picUrl = Constants.BASE_URL + "/commons/attachment/download/"
				+ doctor.getPicUrl();
		fb.display(head, picUrl);
		fb.configLoadfailImage(R.drawable.set_change_userdata_icon);
		doctorName.setText(doctor.getName());
		doctorDes.setText(doctor.getBrief());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.doctor_reply_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		case R.id.doctor_reply_tv_detail:
			DoctorDialog dialog = new DoctorDialog(context, doctor.getName(),
					doctor.getBrief());
			dialog.show();
			break;

		default:
			break;
		}
	}

	private class GetHistoryDetailHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				detail = (HistoryDetail) msg.obj;
				setData();
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

	private void setData() {
		reqStatus.setText(detail.getReqSta());
		if (detail.getReqSta().equals("已接受")) {
			reqStatus.setTextColor(Color.rgb(51, 204, 102));
			doctorReply.setVisibility(View.GONE);
			doctorReply2.setVisibility(View.GONE);
		} else if (detail.getReqSta().equals("待处理")) {
			reqStatus.setTextColor(Color.rgb(255, 103, 39));
			doctorReply.setVisibility(View.GONE);
			doctorReply2.setVisibility(View.GONE);
		} else {
			reqStatus.setTextColor(Color.rgb(232, 115, 98));
			doctorReply2.setVisibility(View.VISIBLE);
			doctorReply.setVisibility(View.VISIBLE);
			doctorReply.setText(detail.getDoctorReply());
		}
		reqType.setText(detail.getReqType());
		reqTime.setText(detail.getReqDate());
		reqContent.setText(detail.getReqContent());
		doctorName.setText(detail.getDoctorName());
		// doctorReply.setText(detail.getDoctorReply());
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(DoctorReplyActivity.this,
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
