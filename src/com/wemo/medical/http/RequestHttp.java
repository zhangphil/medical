package com.wemo.medical.http;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.util.Constants;

/**
 * 
 * @author baiqiao
 * @see 所有网络请求
 * 
 */
@SuppressLint("ShowToast")
public class RequestHttp {

	private static final String TAG = "RequestHttp";
	/**
	 * 网络请求失败
	 */
	public static final int REQUEST_FAILER = 0;

	/**
	 * 网络请求成功
	 */
	public static final int REQUEST_SUCCESS = 1;

	/**
	 * 网络连接异常
	 */
	public static final int NO_NETWORK_CONNECTION = 0;

	/**
	 * 请求参数不正确
	 */
	public static final int ERROR_PARAM = 400;

	/**
	 * 错误请求方式
	 */
	public static final int ERROR_REQUEST_METHOD = 405;

	/**
	 * 未知错误
	 */
	public static final int UNKNOW_ERROR = 500;
	/**
	 * 服务器异常
	 */
	public static final int SERVICE_ERROR = 502;
	private static RequestHttp Instance;
	private FinalHttp finalHttp;
	private AjaxParams params;
	private JsonHelper jsonHelper;
	private static Context context;

	// private String charset = "UTF-8";

	public static RequestHttp getInstance(Context context) {
		if (Instance == null) {
			Instance = new RequestHttp(context);
		}
		return Instance;
	}

	@SuppressWarnings("static-access")
	public RequestHttp(Context context) {
		finalHttp = new FinalHttp();
		jsonHelper = new JsonHelper();
		RequestHttp.context = context;
	}

	/**
	 * 网络请求失败，Toast提示
	 * 
	 * @param errNo
	 *            服务器返回的错误码
	 * @param events
	 *            功能
	 */
	public static void badRequest(int errNo, String events) {
		switch (errNo) {
		case NO_NETWORK_CONNECTION:
			Constants.showToast(context, "网络异常，请检查！");
			break;
		case ERROR_REQUEST_METHOD:
			Constants.showToast(context, "请求方式错误，请更换请求方式！");
			break;

		case ERROR_PARAM:
			if (events.equals("login")) {
				Constants.showToast(context, "登录名或密码错误，请重试！");
			} else {
				Constants.showToast(context, "提交参数不正确！");
			}

			break;

		case UNKNOW_ERROR:
			Constants.showToast(context, "未知错误,请稍后重试！");
			break;

		case SERVICE_ERROR:
			Constants.showToast(context, "服务器异常,请稍后重试！");
			break;

		default:
			Constants.showToast(context, "未知错误,请稍后重试！");
			break;
		}
	}

	/**
	 * 获取字典集
	 * 
	 * @param mHandler
	 */
	public void requestGetDictionary(final Handler mHandler) {
		String url = Constants.BASE_URL + "/api/dict";
		finalHttp.post(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "getDictionaryFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "getDictionarySuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getDictionary(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 用户登录请求
	 * 
	 * @param mHandler
	 * @param handsetNo
	 *            用户登录电话号码
	 * @param passWord
	 *            用户登录密码
	 */
	public void requestLogin(final Handler mHandler, String handsetNo,
			String passWord) {
		String url = Constants.BASE_URL + "/login/rest";
		params = new AjaxParams();
		params.put("handsetNo", handsetNo);
		params.put("password", passWord);
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "loginFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "loginSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getLoginCustomerInfo(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 根据id获取个人客户
	 * 
	 * @param mHandler
	 * @param customerId
	 *            客户id
	 */
	public void requestGetCustomer(final Handler mHandler, String customerId) {
		String url = Constants.BASE_URL + "/api/single/customer/get";
		params = new AjaxParams();
		params.put("id", customerId);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "getCustomerFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "getCustomerSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getCustomerInfo(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * @see 更新个人客户
	 * @param mHandler
	 * @param customerInfo
	 *            个人客户实体类
	 */
	public void requestUpdateCustomer(final Handler mHandler,
			CustomerInfo customerInfo) {
		String url = Constants.BASE_URL + "/api/single/customer/update/mobile";
		params = new AjaxParams();
		params.put("id", customerInfo.getId());
		params.put("cusName", customerInfo.getCusName());
		params.put("birth", customerInfo.getBirth());
		params.put("handsetNo", customerInfo.getHandsetNo());
		params.put("sex.id", customerInfo.getSex().getId());
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "updateCustomerFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "updateCustomerSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = t.toString();
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 添加健管请求
	 * 
	 * @param mHandler
	 * @param reqType
	 *            健管请求类型
	 * @param reqChn
	 *            健管请求渠道
	 * @param appotDt
	 *            预约日期
	 * @param healMng
	 *            健管师
	 * @param cus
	 *            客户
	 * @param reqContent
	 *            健管请求内容
	 */
	public void requestAddApply(final Handler mHandler, String reqType,
			String reqChn, String appotDt, String healMng, String cus,
			String reqContent) {
		String url = Constants.BASE_URL + "/api/health/apply/save";
		params = new AjaxParams();
		params.put("reqType.id", reqType);
		params.put("reqChn.id", reqChn);
		params.put("appotDt", appotDt);
		params.put("healMng.id", healMng);
		params.put("cus.id", cus);
		params.put("reqContent", reqContent);

		Log.d(TAG, params.getParamString());
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "addApplyFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "addApplySuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = t.toString();
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 添加投诉
	 * 
	 * @param mHandler
	 * @param cusId
	 *            客户id
	 * @param suggestedId
	 *            被投诉人id
	 * @param fromType
	 *            投诉渠道
	 * @param reason
	 *            投诉原因
	 * @param suggestType
	 *            投诉类别
	 */
	public void requestAddSuggest(final Handler mHandler, String cusId,
			String suggestedId, String fromType, String reason,
			String suggestType) {
		String url = Constants.BASE_URL + "/api/suggest/save";
		params = new AjaxParams();
		params.put("cus.id", cusId);
		params.put("beSuggested.id", suggestedId);
		params.put("reason", reason);
		params.put("suggestType.id", suggestType);
		params.put("fromType", fromType);
		Log.d(TAG, params.getParamString());
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "addSuggestyFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "addSuggestSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = t.toString();
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取被投诉人列表
	 */
	public void requestComplaintsedInfo(final Handler mHandler) {
		String url = Constants.BASE_URL + "/api/user/list";
		params = new AjaxParams();
		finalHttp.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "addGetcomplaintsedFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "addGetcomplaintsedSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getComplainted(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取我的健管计划列表
	 * 
	 * @param mHandler
	 * @param cid
	 *            客户id
	 */
	public void requestGetPlan(final Handler mHandler, String cid) {
		String url = Constants.BASE_URL + "/api/health/task/list/cus";
		params = new AjaxParams();
		params.put("cid", cid);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetPlanFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetPlanSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				List<Object> obj = new ArrayList<Object>();
				obj.add(jsonHelper.getPlans(t.toString()));
				obj.add(jsonHelper.getTimes(t.toString()));
				msg.obj = obj;
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取我的健管师历史请求列表
	 * 
	 * @param mHandler
	 * @param cid
	 *            客户id
	 */
	public void requestGetHistory(final Handler mHandler, String cid,
			String page) {
		String url = Constants.BASE_URL + "/api/health/apply/list";
		params = new AjaxParams();
		params.put("cus.id", cid);
		params.put("p", page);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetHistoryFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetHistorySuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getHistory(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取健管历史请求详情
	 * 
	 * @param mHandler
	 * @param id
	 *            历史请求id
	 */
	public void requestGetHistoryDetail(final Handler mHandler, String id) {
		String url = Constants.BASE_URL + "/api/health/apply/get";
		params = new AjaxParams();
		params.put("id", id);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetHistoryDetailFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetHistoryDetailSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getHistoryDetail(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 修改客户登录密码
	 * 
	 * @param mHandler
	 * @param cid
	 *            客户id
	 * @param plainPassword
	 *            客户原始密码
	 */
	public void requestChangePassword(final Handler mHandler, String cid,
			String plainPassword) {
		String url = Constants.BASE_URL
				+ "/api/single/customer/update/password";
		params = new AjaxParams();
		params.put("id", cid);
		params.put("plainPassword", plainPassword);
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "ChangePasswordFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "ChangePasswordSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = t.toString();
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 用户修改密码时密码验证
	 * 
	 * @param mHandler
	 * @param cid
	 * @param pwd
	 */
	public void requestCheckPassword(final Handler mHandler, String cid,
			String pwd) {
		String url = Constants.BASE_URL + "/api/single/check_password";
		params = new AjaxParams();
		params.put("cid", cid);
		params.put("pwd", pwd);
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "CheckPasswordFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "CheckPasswordSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = t.toString();
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取投诉列表
	 * 
	 * @param mHandler
	 * @param id
	 *            客户id
	 */
	public void requestGetComplaintList(final Handler mHandler, String id) {
		String url = Constants.BASE_URL + "/api/suggest/list";
		params = new AjaxParams();
		params.put("cus.id", id);
		Log.d(TAG, params.getParamString());
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetComplaintListFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetComplaintListSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getComplaintsList(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取客户风险评估报告列表
	 * 
	 * @param mHandler
	 * @param handSetNo
	 */
	public void requestGetRiskReport(final Handler mHandler, String id,
			String pageSize, String page) {
		String url = Constants.BASE_URL + "/api/single/assess/list";
		params = new AjaxParams();
		params.put("s", pageSize);
		params.put("p", page);
		params.put("cus.id", id);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetRiskReportFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetRiskReportSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getRiskReport(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 百度推送提交设备信息
	 * 
	 * @param cusId
	 * @param userId
	 * @param channelId
	 */
	public void requestSubmitBaiduPush(String cusId, String userId,
			String channelId) {
		String url = Constants.BASE_URL + "/api/single/customer/bind/baidu";
		params = new AjaxParams();
		params.put("id", cusId);
		params.put("userId", userId);
		params.put("channelId", channelId);
		Log.d(TAG, "params:  " + params.getParamString());
		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "SubmitBaiduPushFailer:  ";
				Log.d(TAG, tags + errorNo);

			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "SubmitBaiduPushSuccess:  ";
				Log.d(TAG, tags + t.toString());
			}
		});
	}

	/**
	 * 获取问卷调查列表
	 * 
	 * @param mHandler
	 * @param cid
	 */
	public void requestGetSurveyList(final Handler mHandler, String cid,
			String page) {
		String url = Constants.BASE_URL + "/api/single/ques/list";
		params = new AjaxParams();
		params.put("cus.id", cid);
		params.put("p", page);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetSurveyListFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetSurveyListSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getRiskReport(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取体检报告列表
	 * 
	 * @param mHandler
	 * @param cid
	 * @param page
	 */
	public void requestGetExamList(final Handler mHandler, String cid,
			String page) {
		String url = Constants.BASE_URL + "/api/single/exam/list";
		params = new AjaxParams();
		params.put("cus.id", cid);
		params.put("p", page);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetExamListFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetExamListSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getExam(t.toString());
				msg.sendToTarget();
			}
		});
	}

	/**
	 * 获取健管师头像和简介
	 * 
	 * @param mHandler
	 * @param id
	 *            健管师id
	 */
	public void requestGetDoctor(final Handler mHandler, String id) {
		String url = Constants.BASE_URL + "/api/user/get";
		params = new AjaxParams();
		params.put("id", id);
		finalHttp.get(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "GetDoctorFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "GetDoctorSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getDoctor(t.toString());
				msg.sendToTarget();
			}
		});
	}
	/**
	 * 获取更新信息
	 * 
	 * @param mHandler
	 */
	public void requestUpdate(final Handler mHandler) {
		String url = Constants.BASE_URL + "/api/sysupdate";
		finalHttp.post(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				String tags = "getUpdateFailer:  ";
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_FAILER;
				msg.obj = errorNo;
				Log.d(TAG, tags + errorNo);
				msg.sendToTarget();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				String tags = "getUpdateSuccess:  ";
				Log.d(TAG, tags + t.toString());
				Message msg = mHandler.obtainMessage();
				msg.what = REQUEST_SUCCESS;
				msg.obj = jsonHelper.getUpdateData(t.toString());
				msg.sendToTarget();
			}
		});
	}
}
