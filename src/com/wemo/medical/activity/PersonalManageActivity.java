package com.wemo.medical.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wemo.medical.R;
import com.wemo.medical.db.SqliteHelper;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Dictionary;
import com.wemo.medical.http.RequestHttp;
import com.wemo.medical.util.ActivityStartAnim;
import com.wemo.medical.util.Constants;
import com.wemo.medical.widget.CircularImage;

@SuppressLint({ "SimpleDateFormat", "ShowToast", "HandlerLeak" })
public class PersonalManageActivity extends Activity implements OnClickListener {

	private String TAG = "PersonalManageActivity";
	private Context context;
	private RequestHttp http;
	private ImageView back;
	private CircularImage head;
	private Spinner spinner;
	private TextView save;
	private TextView birth;
	private EditText name;
	private EditText phone;
	private String[] sex;
	private String sName, sPhone, sSexId, sBirth;
	private int year, month, day; // 客户生日的年月日
	private List<Dictionary> dictionaries;
	private MedicalApplication application;
	private GetCustomerHandler getCustomerHandler;
	private CustomerInfo customerInfo;
	private ArrayAdapter<String> spinnerAdapter;
	private File takePhoto; // 拍照获取的照片
	private File choicePhoto; // 拍照获取的照片
	private int crop = 300; // 头像裁剪尺寸
	private String token;
	private boolean isChangePhone = false;
	/**
	 * 获取客户出生日期Handler
	 */
	public static Handler getBirthHandler;
	private int getCusinfoStatus = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_manage);

		initMembers();
		initView();
		createdHolder();
		setHandler();
	}

	/**
	 * 实例化控件
	 */
	private void initView() {

		back = (ImageView) this.findViewById(R.id.personal_img_back);
		head = (CircularImage) this.findViewById(R.id.personal_img_head);
		save = (TextView) this.findViewById(R.id.personal_btn_save);
		spinner = (Spinner) this.findViewById(R.id.personal_spinner_sex);
		birth = (TextView) this.findViewById(R.id.personal_btn_date);
		head.setImageResource(R.drawable.set_change_userdata_icon);
		name = (EditText) this.findViewById(R.id.personal_et_name);
		phone = (EditText) this.findViewById(R.id.personal_et_phone);

		back.setOnClickListener(this);
		head.setOnClickListener(this);
		save.setOnClickListener(this);
		birth.setOnClickListener(this);
	}

	/**
	 * 实例化成员变量
	 */
	private void initMembers() {
		context = PersonalManageActivity.this;
		application = (MedicalApplication) getApplication();
		getCustomerHandler = new GetCustomerHandler();
		customerInfo = application.getCustomerInfo();
		token = customerInfo.getToken();
		takePhoto = new File(Environment.getExternalStorageDirectory()
				+ "/medical", getTakePhotoFileName());
		choicePhoto = new File(Environment.getExternalStorageDirectory()
				+ "/medical", getChoicePhotoFileName());
		http = RequestHttp.getInstance(context);
		http.requestGetCustomer(getCustomerHandler, customerInfo.getId());
		showDialog();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		name.setText(customerInfo.getCusName());
		birth.setText(customerInfo.getBirth());
		year = Integer.parseInt(customerInfo.getBirth().substring(0, 4));
		month = Integer.parseInt(customerInfo.getBirth().substring(5, 7));
		day = Integer.parseInt(customerInfo.getBirth().substring(8, 10));
		phone.setText(customerInfo.getHandsetNo());
	}

	private void setHandler() {
		getBirthHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 200) {
					birth.setText(msg.obj.toString());
				}
			}
		};
	}

	private void submitData() {
		sName = name.getText().toString();
		sBirth = birth.getText().toString();
		sPhone = phone.getText().toString();
		if (sName.equals("") || sBirth.equals("") || sPhone.equals("")) {
			Constants.showToast(context, "信息填写不完整！");
		} else {
			if (sPhone.length() != 11) {
				Constants.showToast(context, "请输入正确的电话号码！");
			} else {
				if (getCusinfoStatus == -1) {
					sSexId = dictionaries.get(
							Integer.parseInt(spinner.getSelectedItemId() + ""))
							.getId();
					if (!sPhone.equals(customerInfo.getHandsetNo())) {
						isChangePhone = true;
					} else {
						isChangePhone = false;
					}
					customerInfo.setCusName(sName);
					customerInfo.setBirth(sBirth);
					customerInfo.setHandsetNo(sPhone);
					Dictionary sex = new Dictionary();
					sex.setId(sSexId);
					customerInfo.setSex(sex);
					UpdateHandler updateHandler = new UpdateHandler();
					http.requestUpdateCustomer(updateHandler, customerInfo);
					showDialog();
				} else if (getCusinfoStatus == 0) {
					Constants.showToast(context, "网络异常，请稍后重试");
				} else if (getCusinfoStatus == 500) {
					Constants.showToast(context, "服务器异常，请稍后重试！");
				} else {
					Constants.showToast(context, "未知错误，请稍后重试！");
				}
			}
		}
	}

	/**
	 * 更新客户信息请求Handler
	 * 
	 */
	private class UpdateHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				ReGetCustomerHandler reGetCustomerHandler = new ReGetCustomerHandler();
				http.requestGetCustomer(reGetCustomerHandler,
						customerInfo.getId());
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

	private class GetCustomerHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				customerInfo = (CustomerInfo) msg.obj;
				initData();
				setSpinner();
				waitDialog.dismiss();
				break;

			case RequestHttp.REQUEST_FAILER:
				waitDialog.dismiss();
				RequestHttp.badRequest(Integer.parseInt(msg.obj.toString()),
						null);
				getCusinfoStatus = Integer.parseInt(msg.obj.toString());
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 客户修改个人信息后请求客户信息Handler
	 */
	private class ReGetCustomerHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RequestHttp.REQUEST_SUCCESS:
				waitDialog.dismiss();
				Constants.showToast(context, "更新成功！");
				customerInfo = (CustomerInfo) msg.obj;
				customerInfo.setToken(token);
				application.setCustomerInfo(customerInfo);
				SqliteHelper helper = new SqliteHelper(context);
				helper.insertCustomerInfo(customerInfo);
				if (isChangePhone) {
					Intent intent = new Intent(PersonalManageActivity.this,
							LoginActivity.class);
					startActivity(intent);
					Message message = MainActivity.finishHandler
							.obtainMessage();
					message.what = 200;
					message.sendToTarget();

					Message message2 = ManagementActivity.finishHandler
							.obtainMessage();
					message2.what = 200;
					message2.sendToTarget();

					application.setCustomerInfo(null);
					helper.deleteCustomerInfo();

					SharedPreferences preferences = context
							.getSharedPreferences("medical", 0);
					Editor editor = preferences.edit();
					editor.putString("handsetNo", "");
					editor.putString("password", "");
					editor.commit();
				}
				PersonalManageActivity.this.finish();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_img_back:
			this.finish();
			ActivityStartAnim.LeftToRightClose(this);
			break;

		case R.id.personal_img_head:
			doPickPhotoAction();
			break;

		case R.id.personal_btn_save:
			submitData();

			break;

		case R.id.personal_btn_date:
			DatePickerDialog dialog = new DatePickerDialog(context, year,
					month, day, 2);
			dialog.show();
			break;

		default:
			break;
		}
	}

	/**
	 * 设置性别选择控件
	 */
	private void setSpinner() {
		SqliteHelper sqliteHelper = new SqliteHelper(context);
		dictionaries = sqliteHelper.getDictionary("sex");
		int index = 0;
		sex = new String[dictionaries.size()];
		for (int i = 0; i < dictionaries.size(); i++) {
			sex[i] = dictionaries.get(i).getLabel();
			if (sex[i].equals(customerInfo.getSex().getLabel())) {
				index = i;
			}

		}
		spinnerAdapter = new ArrayAdapter<String>(context,
				R.layout.item_spinner, sex);
		spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spinner.setAdapter(spinnerAdapter);
		spinner.setSelection(index);
	}

	/**
	 * 在sdcard上创建名为medical的文件夹，用于保存头像
	 */
	private void createdHolder() {
		if (isHaveSdCard()) {
			File medical = new File(Environment.getExternalStorageDirectory()
					+ "/medical");

			if (!medical.exists()) {
				medical.mkdirs();
			}
		}
	}

	/**
	 * 判断sdcard是否插入
	 */
	private boolean isHaveSdCard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 弹出对话框，选择头像来源
	 */
	private void doPickPhotoAction() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		String choices[] = new String[] { "拍照", "从相册选择" };
		ListAdapter adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, choices);
		builder.setTitle("请选择头像来源");
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (which == 0) {
							// 选择拍照
							Intent cameraintent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 指定调用相机拍照后照片的储存路径
							cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(takePhoto));
							startActivityForResult(cameraintent, 101);

						} else {
							Intent intent = new Intent(
									"android.intent.action.PICK");
							intent.setDataAndType(
									MediaStore.Images.Media.INTERNAL_CONTENT_URI,
									"image/*");
							intent.putExtra("output", Uri.fromFile(choicePhoto));
							intent.putExtra("crop", "true");
							intent.putExtra("aspectX", 1);// 裁剪框比例
							intent.putExtra("aspectY", 1);
							intent.putExtra("outputX", crop);// 输出图片大小
							intent.putExtra("outputY", crop);
							startActivityForResult(intent, 100);
						}
					}
				});
		builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 使用系统当前日期加以调整作为拍照照片的名称
	private String getTakePhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return "takePhoto_" + dateFormat.format(date) + ".png";
	}

	// 使用系统当前日期加以调整作为相册选取照片的名称
	private String getChoicePhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date) + ".png";
	}

	private void showDialog() {
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog_upload, null);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

		waitDialog = Constants.createLoadingDialog(PersonalManageActivity.this,
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
