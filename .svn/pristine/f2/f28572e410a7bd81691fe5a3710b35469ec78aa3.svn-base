package com.wemo.medical.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.db.SqliteHelper;
import com.wemo.medical.entity.Complainted;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Dictionary;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.Constants;

@SuppressLint("HandlerLeak")
public class ComplaintsDialog extends Dialog implements
		android.view.View.OnClickListener {

	private final String TAG = "ComplaintsDialog";
	private Context context;
	private Spinner kind;
	private Spinner person;
	private TextView cancel;
	private TextView confirm;
	private EditText reason;
	private String[] sKinds;
	private String[] sPersons;
	private RequestHttp http;
	private List<Complainted> complainteds;
	private List<Dictionary> dicts;
	private SqliteHelper helper;
	private MedicalApplication application;
	private CustomerInfo cusInfo;
	private boolean getDoctorStatus = false;

	public ComplaintsDialog(Context context) {
		super(context, R.style.dialog);

		setContentView(R.layout.dialog_complaints);
		getWindow().setLayout(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		this.context = context;

		initView();
		initMembers();
		setSpinnerKind();
	}

	private void initView() {
		kind = (Spinner) this.findViewById(R.id.dialog_complaints_spinner_kind);
		person = (Spinner) this
				.findViewById(R.id.dialog_complaints_spinner_complainted);
		reason = (EditText) this.findViewById(R.id.dialog_complaints_reason);
		confirm = (TextView) this.findViewById(R.id.dialog_complaints_cancel);
		cancel = (TextView) this.findViewById(R.id.dialog_complaints_confirm);
		reason = (EditText) this.findViewById(R.id.dialog_complaints_reason);

		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);

	}

	private void initMembers() {
		application = (MedicalApplication) context.getApplicationContext();
		cusInfo = application.getCustomerInfo();
		helper = new SqliteHelper(context);
		dicts = helper.getDictionary("hm_suggest_type");
		Log.d(TAG + "  dicts.size", dicts.size() + "");
		sKinds = new String[dicts.size()];
		for (int i = 0; i < dicts.size(); i++) {
			sKinds[i] = dicts.get(i).getLabel();
		}
		http = RequestHttp.getInstance(context);
		GetComplaintedHandler complaintedHandler = new GetComplaintedHandler();
		http.requestComplaintsedInfo(complaintedHandler);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_complaints_cancel:
			dismiss();
			break;

		case R.id.dialog_complaints_confirm:
			submit();
			break;

		default:
			break;
		}
	}

	private void submit() {
		if (reason.getText().toString().equals("")) {
			Constants.showToast(context, "请输入投诉原因！");
		} else {
			String sReason = reason.getText().toString();
			String complaintedId = null;
			if (getDoctorStatus) {
				complaintedId = complainteds.get(
						Integer.parseInt(person.getSelectedItemId() + ""))
						.getId();
				Log.d(TAG + "  selectId",
						Integer.parseInt(person.getSelectedItemId() + "") + "");
				Log.d(TAG + "  selectId",
						complainteds.get(
								Integer.parseInt(person.getSelectedItemId()
										+ "")).getNo());
				String suggestType = dicts.get(
						Integer.parseInt(kind.getSelectedItemId() + ""))
						.getId();
				AddComplaintesHandler handler = new AddComplaintesHandler();
				http.requestAddSuggest(handler, cusInfo.getId(), complaintedId,
						"2", sReason, suggestType);
				dismiss();
			} else {
				Constants.showToast(context, "获取健管师列表失败，请检查网络后重试！");
				dismiss();
			}

		}
	}

	private void setSpinnerKind() {
		ArrayAdapter<String> adapterKind = new ArrayAdapter<String>(context,
				R.layout.item_spinner, sKinds);
		adapterKind.setDropDownViewResource(R.layout.item_spinner_dropdown);
		kind.setAdapter(adapterKind);

	}

	/**
	 * 获取投诉人列表Handler
	 * 
	 * @author baiqiao
	 * 
	 */
	private class GetComplaintedHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				complainteds = (List<Complainted>) msg.obj;
				sPersons = new String[complainteds.size()];
				for (int i = 0; i < complainteds.size(); i++) {
					sPersons[i] = complainteds.get(i).getName();
				}
				ArrayAdapter<String> adapterPerson = new ArrayAdapter<String>(
						context, R.layout.item_spinner, sPersons);
				adapterPerson
						.setDropDownViewResource(R.layout.item_spinner_dropdown);
				person.setAdapter(adapterPerson);
				getDoctorStatus = true;
				break;

			case RequestHttp.REQUEST_FAILER:
				Constants.showToast(context, "被投诉人获取失败！");
				getDoctorStatus = false;
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 提交投诉handler
	 * 
	 * @author baiqiao
	 * 
	 */
	private class AddComplaintesHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				Constants.showToast(context, "提交成功,请下拉刷新查看");

				break;

			case RequestHttp.REQUEST_FAILER:
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				break;

			default:
				break;
			}
		}
	}
}
