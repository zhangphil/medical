package com.wemo.medical.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

import com.wemo.medical.entity.Complainted;
import com.wemo.medical.entity.Complaints;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Dictionary;
import com.wemo.medical.entity.Doctor;
import com.wemo.medical.entity.Exam;
import com.wemo.medical.entity.History;
import com.wemo.medical.entity.HistoryDetail;
import com.wemo.medical.entity.Plan;
import com.wemo.medical.entity.PlanRemainTimes;
import com.wemo.medical.entity.RiskReport;
import com.wemo.medical.entity.UpdateData;

/**
 * @author baiqiao
 */
@SuppressLint("SimpleDateFormat")
public class JsonHelper {

	private final String TAG = "JsonHelper";

	/**
	 * @see 客户登录信息解析
	 */
	public CustomerInfo getLoginCustomerInfo(String json) {
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			//Obj代表JSONObject的整体初始化
			JSONObject obj = new JSONObject(json);
			//customerInfo设置token属性唯整体JSONObject的token属性
			customerInfo.setToken(obj.getString("token"));
			Log.d(TAG, "getCustomerInfo" + obj.getString("token"));
			JSONObject user = obj.getJSONObject("user");
			customerInfo.setId(user.getString("id"));
			customerInfo.setCusName(user.getString("cusName"));
			customerInfo.setNameFullSpel(user.getString("nameFullSpel"));

			JSONObject jsonSex = user.getJSONObject("sex");
			Dictionary sex = new Dictionary();
			sex.setId(jsonSex.getString("id"));
			sex.setLabel(jsonSex.getString("label"));
			sex.setType(jsonSex.getString("type"));
			sex.setDescription(jsonSex.getString("description"));
			sex.setSort(jsonSex.getString("sort"));
			sex.setValue(jsonSex.getString("value"));
			customerInfo.setSex(sex);

			customerInfo.setBirth(user.getString("birth"));

			JSONObject jsonMarriage = user.getJSONObject("marriage");
			Dictionary marriage = new Dictionary();
			marriage.setId(jsonMarriage.getString("id"));
			marriage.setLabel(jsonMarriage.getString("label"));
			marriage.setType(jsonMarriage.getString("type"));
			marriage.setDescription(jsonMarriage.getString("description"));
			marriage.setSort(jsonMarriage.getString("sort"));
			marriage.setValue(jsonMarriage.getString("value"));
			customerInfo.setMarriage(marriage);
			
			
			JSONObject jsonCusType = user.getJSONObject("cusType");
			customerInfo.setCusTypeId(jsonCusType.getString("id"));
			Dictionary cusType = new Dictionary();
			cusType.setId(jsonCusType.getString("id"));
			cusType.setLabel(jsonCusType.getString("label"));
			cusType.setType(jsonCusType.getString("type"));
			cusType.setDescription(jsonCusType.getString("description"));
			cusType.setSort(jsonCusType.getString("sort"));
			cusType.setValue(jsonCusType.getString("value"));
			customerInfo.setCusType(cusType);

			customerInfo.setCusLvl(user.getString("cusLvl"));
			customerInfo.setCorpId(user.getString("corpId"));
			customerInfo.setHandsetNo(user.getString("handsetNo"));
			customerInfo.setPermRes(user.getString("permRes"));
			customerInfo.setEmail(user.getString("email"));
			customerInfo.setQq(user.getString("qq"));
			customerInfo.setContName(user.getString("contName"));

			if (!user.getString("contRela").equals("null")) {
				JSONObject jsonContRela = user.getJSONObject("contRela");
				Dictionary contRela = new Dictionary();
				contRela.setId(jsonContRela.getString("id"));
				contRela.setLabel(jsonContRela.getString("label"));
				contRela.setType(jsonContRela.getString("type"));
				contRela.setDescription(jsonContRela.getString("description"));
				contRela.setSort(jsonContRela.getString("sort"));
				contRela.setValue(jsonContRela.getString("value"));
				customerInfo.setContRela(contRela);
			}

			customerInfo.setContHandsetNo(user.getString("contHandsetNo"));
			customerInfo.setRemark(user.getString("remark"));

			customerInfo.setMngAllocRzn(user.getString("mngAllocRzn"));
			customerInfo.setCusStat(user.getString("cusStat"));
			customerInfo.setCrtDt(user.getString("crtDt"));
			customerInfo.setCrtId(user.getString("crtId"));
			customerInfo.setUpdDt(user.getString("updDt"));
			customerInfo.setUpdId(user.getString("updId"));
			customerInfo.setDelDt(user.getString("delDt"));
			customerInfo.setDelId(user.getString("delId"));
			if (user.has("healMng")) {
				JSONObject healMng = user.getJSONObject("healMng");
				customerInfo.setHealMngName(healMng.getString("name"));
				customerInfo.setHealMngId(healMng.getString("id"));
				Log.d(TAG, "setHealMngId:  " + customerInfo.getHealMngId()
						+ "  " + customerInfo.getHealMngName());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

	/**
	 * @see 客户信息解析
	 */
	public CustomerInfo getCustomerInfo(String json) {
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			JSONObject customer = new JSONObject(json)
					.getJSONObject("customer");
			customerInfo.setId(customer.getString("id"));
			customerInfo.setCusName(customer.getString("cusName"));
			customerInfo.setNameFullSpel(customer.getString("nameFullSpel"));
			customerInfo.setBirth(customer.getString("birth"));
			customerInfo.setCusLvl(customer.getString("cusLvl"));
			customerInfo.setCorpId(customer.getString("corpId"));
			customerInfo.setHandsetNo(customer.getString("handsetNo"));
			customerInfo.setPermRes(customer.getString("permRes"));
			customerInfo.setEmail(customer.getString("email"));
			customerInfo.setQq(customer.getString("qq"));
			customerInfo.setContName(customer.getString("contName"));
			customerInfo.setContHandsetNo(customer.getString("contHandsetNo"));
			customerInfo.setRemark(customer.getString("remark"));

			customerInfo.setMngAllocRzn(customer.getString("mngAllocRzn"));
			customerInfo.setCusStat(customer.getString("cusStat"));
			customerInfo.setCrtDt(customer.getString("crtDt"));
			customerInfo.setCrtId(customer.getString("crtId"));
			customerInfo.setUpdDt(customer.getString("updDt"));
			customerInfo.setUpdId(customer.getString("updId"));
			customerInfo.setDelDt(customer.getString("delDt"));
			customerInfo.setDelId(customer.getString("delId"));

			JSONObject jsonSex = customer.getJSONObject("sex");
			Dictionary sex = new Dictionary();
			sex.setId(jsonSex.getString("id"));
			sex.setLabel(jsonSex.getString("label"));
			sex.setType(jsonSex.getString("type"));
			sex.setDescription(jsonSex.getString("description"));
			sex.setSort(jsonSex.getString("sort"));
			sex.setValue(jsonSex.getString("value"));
			customerInfo.setSex(sex);

			JSONObject jsonMarriage = customer.getJSONObject("marriage");
			Dictionary marriage = new Dictionary();
			marriage.setId(jsonMarriage.getString("id"));
			marriage.setLabel(jsonMarriage.getString("label"));
			marriage.setType(jsonMarriage.getString("type"));
			marriage.setDescription(jsonMarriage.getString("description"));
			marriage.setSort(jsonMarriage.getString("sort"));
			marriage.setValue(jsonMarriage.getString("value"));
			customerInfo.setMarriage(marriage);

			JSONObject jsonCusType = customer.getJSONObject("cusType");
			customerInfo.setCusTypeId(jsonCusType.getString("id"));
			Dictionary cusType = new Dictionary();
			cusType.setId(jsonCusType.getString("id"));
			cusType.setLabel(jsonCusType.getString("label"));
			cusType.setType(jsonCusType.getString("type"));
			cusType.setDescription(jsonCusType.getString("description"));
			cusType.setSort(jsonCusType.getString("sort"));
			cusType.setValue(jsonCusType.getString("value"));
			customerInfo.setMarriage(cusType);

			if (!customer.getString("contRela").equals("null")) {
				JSONObject jsonContRela = customer.getJSONObject("contRela");
				Dictionary contRela = new Dictionary();
				contRela.setId(jsonContRela.getString("id"));
				contRela.setLabel(jsonContRela.getString("label"));
				contRela.setType(jsonContRela.getString("type"));
				contRela.setDescription(jsonContRela.getString("description"));
				contRela.setSort(jsonContRela.getString("sort"));
				contRela.setValue(jsonContRela.getString("value"));
				customerInfo.setContRela(contRela);
			}

			if (customer.has("healMng")) {
				JSONObject healMng = customer.getJSONObject("healMng");
				customerInfo.setHealMngName(healMng.getString("name"));
				customerInfo.setHealMngId(healMng.getString("id"));
				Log.d(TAG, customerInfo.getHealMngId());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

	/**
	 * 字典集解析
	 */
	public List<Dictionary> getDictionary(String json) {
		List<Dictionary> dictionaries = new ArrayList<Dictionary>();
		try {
			JSONArray array = new JSONObject(json).getJSONArray("dicts");
			for (int i = 0; i < array.length(); i++) {
				Dictionary dictionary = new Dictionary();
				JSONObject obj = array.getJSONObject(i);
				dictionary.setDescription(obj.getString("description"));
				dictionary.setId(obj.getString("id"));
				dictionary.setLabel(obj.getString("label"));
				dictionary.setSort(obj.getString("sort"));
				dictionary.setType(obj.getString("type"));
				dictionary.setValue(obj.getString("value"));
				dictionaries.add(dictionary);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dictionaries;
	}

	/**
	 * 被投诉人列表解析
	 */
	public List<Complainted> getComplainted(String json) {
		List<Complainted> complainteds = new ArrayList<Complainted>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Complainted complainted = new Complainted();
				complainted.setId(obj.getString("id"));
				complainted.setName(obj.getString("name"));
				complainted.setNo(obj.getString("no"));
				complainteds.add(complainted);
				Log.d(TAG, complainted.getNo() + complainted.getName());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return complainteds;
	}

	/**
	 * 我的健管计划解析
	 * 
	 * @param json
	 * @return
	 */
	public List<Plan> getPlans(String json) {
		List<Plan> plans = new ArrayList<Plan>();
		try {
			JSONArray array = new JSONObject(json).getJSONArray("tasks");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Plan plan = new Plan();
				plan.setDate(obj.getString("startDt"));
				JSONObject type = obj.getJSONObject("taskType");
				plan.setContent(obj.getString("taskContent"));
				plan.setName(type.getString("label"));
				plans.add(plan);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return plans;
	}

	/**
	 * 健管计划剩余次数解析
	 * 
	 * @param json
	 * @return
	 */
	public List<PlanRemainTimes> getTimes(String json) {
		List<PlanRemainTimes> times = new ArrayList<PlanRemainTimes>();
		List<PlanRemainTimes> timesTrue = new ArrayList<PlanRemainTimes>();
		try {
			JSONArray array = new JSONObject(json)
					.getJSONArray("planServiceTimes");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				PlanRemainTimes planRemainTimes = new PlanRemainTimes();
				planRemainTimes.setId(obj.getString("id"));
				planRemainTimes.setTimes(obj.getString("remainTimes"));
				JSONObject type = obj.getJSONObject("taskType");
				planRemainTimes.setType(type.getString("label"));
				times.add(planRemainTimes);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (times.size() > 0) {
			timesTrue.add(times.get(0));

			int j = 0;
			for (int i = 1; i < times.size(); i++) {
				if (times.get(i).getType().equals(times.get(i - 1).getType())) {
					
					timesTrue.get(i - j).setTimes(
							Integer.parseInt(times.get(i).getTimes())
									+ Integer.parseInt(timesTrue.get(i - j)
											.getTimes()) + "");
					j++;
				} else {
					timesTrue.add(times.get(i));
					j--;
				}
			}
		}

		return timesTrue;
	}

	/**
	 * 我的健管师历史请求数据解析
	 * 
	 * @param json
	 * @return
	 */
	public List<History> getHistory(String json) {
		List<History> histories = new ArrayList<History>();
		try {
			JSONArray array = new JSONObject(json).getJSONObject("data")
					.getJSONArray("result");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				History history = new History();
				history.setDate(obj.getString("reqTm"));
				history.setEvents(obj.getString("reqType"));
				history.setId(obj.getString("id"));
				if (obj.getString("reqStat").equals("已拒绝")) {
					history.setStatus("0");
				} else if (obj.getString("reqStat").equals("已接受")) {
					history.setStatus("1");
				} else {
					history.setStatus("2");
				}
				Log.d(TAG, "status:  " + history.getStatus());
				histories.add(history);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return histories;

	}

	/**
	 * 健管历史请求详情解析
	 * 
	 * @param json
	 * @return
	 */
	public HistoryDetail getHistoryDetail(String json) {
		HistoryDetail detail = new HistoryDetail();
		try {
			JSONObject obj = new JSONObject(json)
					.getJSONObject("requestRecord");
			detail.setReqDate(obj.getString("appotDt"));
			detail.setReqContent(obj.getString("reqContent"));
			if (obj.getString("refuseRzn").equals("null")) {
				detail.setDoctorReply("");
			} else {
				detail.setDoctorReply(obj.getString("refuseRzn"));
			}

			JSONObject doctor = obj.getJSONObject("healMng");
			detail.setDoctorName(doctor.getString("name"));
			detail.setDoctorImg(doctor.getString("avatarUrl"));

			JSONObject type = obj.getJSONObject("reqType");
			detail.setReqType(type.getString("label"));

			JSONObject status = obj.getJSONObject("reqStat");
			detail.setReqSta(status.getString("label"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return detail;
	}

	/**
	 * 客户投诉列表解析
	 * 
	 * @param json
	 * @return 投诉列表
	 */
	public List<Complaints> getComplaintsList(String json) {
		List<Complaints> complaints = new ArrayList<Complaints>();
		try {
			JSONArray array = new JSONObject(json).getJSONObject("data")
					.getJSONArray("result");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Complaints complain = new Complaints();
				complain.setDate(obj.getString("createdDate"));
				complain.setReason(obj.getString("reason"));
				JSONObject status = obj.getJSONObject("status");
				complain.setStatus(status.getString("value"));
				JSONObject type = obj.getJSONObject("suggestType");
				complain.setName(type.getString("label"));
				complaints.add(complain);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return complaints;

	}

	/**
	 * 风险评估报告列表解析
	 * 
	 * @param json
	 * @return
	 */
	public List<RiskReport> getRiskReport(String json) {
		List<RiskReport> riskReports = new ArrayList<RiskReport>();
		try {
			JSONArray array = new JSONObject(json).getJSONObject("data")
					.getJSONArray("result");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				RiskReport riskReport = new RiskReport();
				riskReport.setAskDate(obj.getString("askDate"));
				riskReport.setId(obj.getString("id"));
				riskReport.setAskId(obj.getString("askId"));
				riskReport.setCusHandsetNo(obj.getString("cusHandsetNo"));
				riskReport.setCusName(obj.getString("cusName"));
				JSONObject sex = obj.getJSONObject("cusSex");
				riskReport.setSex(sex.getString("label"));
				riskReports.add(riskReport);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return riskReports;
	}

	/**
	 * 体检报告列表解析
	 * 
	 * @param json
	 * @return
	 */
	public List<Exam> getExam(String json) {
		List<Exam> exams = new ArrayList<Exam>();
		try {
			JSONArray array = new JSONObject(json).getJSONObject("examPage")
					.getJSONArray("result");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Exam exam = new Exam();
				exam.setDate(obj.getString("createDate"));
				exam.setId(obj.getString("id"));
				JSONObject cusInfo = obj.getJSONObject("cusInfo");
				exam.setHandsetNo(cusInfo.getString("handsetNo"));
				exam.setName(cusInfo.getString("name"));
				exam.setExamNo(obj.getString("examNo"));
				exam.setSex(cusInfo.getString("sex"));
				exams.add(exam);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return exams;
	}

	public Doctor getDoctor(String json) {
		Doctor doctor = new Doctor();
		try {
			JSONObject obj = new JSONObject(json).getJSONObject("user");
			JSONObject pic = obj.getJSONObject("picture");
			if (obj.getString("brief").equals("null")) {
				doctor.setBrief("");
			} else {
				doctor.setBrief(obj.getString("brief"));
			}
			doctor.setName(obj.getString("roleNames"));
			doctor.setPicUrl(pic.getString("id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return doctor;
	}
	public UpdateData getUpdateData(String json) {
		UpdateData updateData = new UpdateData();
		try {
			JSONObject obj = new JSONObject(json).getJSONObject("sysupdate");
			updateData.setApkName(obj.getString("apkName"));
			updateData.setApkUrl(obj.getString("apkUrl"));
			updateData.setAppId(obj.getString("appId"));
			updateData.setAppName(obj.getString("appName"));
			updateData.setVerCode(obj.getString("verCode"));
			updateData.setVerName(obj.getString("verName"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return updateData;
	}
}
